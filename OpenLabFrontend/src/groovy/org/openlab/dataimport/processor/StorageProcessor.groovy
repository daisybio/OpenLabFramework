package org.openlab.dataimport.processor

import org.openlab.main.*;
import org.openlab.genetracker.*;
import org.openlab.genetracker.vector.*;
import org.openlab.storage.*;
import org.joda.time.*;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.openlab.dataimport.processor.*;
import org.openlab.dataimport.*;
import org.openlab.dataimport.creator.*;
import org.openlab.main.DataObject;
import groovy.transform.InheritConstructors

@InheritConstructors
class StorageProcessor extends GeneTrackerImportClass{

	def boxCreationService = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("boxCreationService")
	
	def freezeDataObjectWithManualStorage(def dataObj, def storageId)
	{	
		def freezers = importer.getFreezers()
		def compartments = importer.getCompartments()
		
		def storage = findStorage(storageId)
		if(!storage) return
		
		def freezerId = storage.freezerId
		def freezer = freezers.find{it.oldId == freezerId}
		def freezerName = freezer?.description?.trim()?.toLowerCase()
		
		def compartmentId = storage.compartmentId
		def compartment = compartments.find{it.oldId == compartmentId}
		def compartmentName = compartment?.description?.trim()?.toLowerCase()
		
		def boxName
		
		if(storage.boxName instanceof Double) boxName = storage.boxName
		else boxName = storage.boxName?.trim()?.toLowerCase()
		
		def description
		
		/* Odense */
		if(boxName && !(boxName instanceof Double) && (boxName.startsWith("gateway") || boxName.startsWith("pdonr") || boxName.startsWith("odense")))
		{
			description = "Gateway-1"
		}
		else if(compartmentName && (compartmentName.startsWith("freezer") || compartmentName.startsWith("wp") || compartmentName.startsWith("047")
			|| compartmentName.startsWith("47") || compartmentName.startsWith("25") || compartmentName.startsWith("room 047")))
		{
			description = "Gateway-1"
		}
		else if(freezerName && (freezerName.startsWith("odense") || freezerName.startsWith("wp")))
		{
			description = "Gateway-1"
		}
		
		/* Heidelberg */
		else if(!(boxName instanceof Double) && boxName?.startsWith("rainer"))
			description = "Rainer${boxName.charAt(boxName.length()-1)}"
			
		else if(!(boxName instanceof Double) && boxName?.startsWith("paul"))
			description = "Paul"
			
		else if(!(boxName instanceof Double) && ((boxName == "platte1") || boxName?.startsWith("rzpd") || (boxName == "strain plate") || compartmentName?.startsWith("rzpd")))
			description = "RZPD strain plate"
		
		else if(boxName == "mgc_klone")
			description = "MGC_Klone"
			
		else if(compartmentName == "rainer")
		{
			try{
				int number = Double.valueOf(boxName).round()
				description = "Rainer${number}"
			}catch(NumberFormatException e)
			{
				log.error "Could not find box for id ${storageId} in compartment Rainer."
			}
		}
		
		else if(boxName == "dna2") description = "DNA"
		
		else if(compartmentName == "dna") description = "DNA"
		
		else if(compartmentName == "dna_platte") description = "DNA"
		
		else if(freezerName == "2.312 (stephi)") description = "DNA"
		
		else if(compartmentName == "primer") description = "Primer"
		
		else if(freezerName == "2.325 (-80)") description = "Primer"
		
		else log.warn "could not match storage id ${storageId} to a box. freezerName: ${freezerName} compartmentName: ${compartmentName} boxName: ${boxName}"
		
		/* Store */
		if(description && description != "")
		{
			def storageElt = createStorageElement(storage, dataObj)
			
			if(storageElt.xcoord < 0 || storageElt.ycoord < 0)
			{
				log.error "could not create storage element for ${storage} and ${dataObj} because of invalid coordinates"
			}
			
			def box = Box.findByDescription(description)
			
			if(box?.elements?.find{it.xcoord == storageElt.xcoord && it.ycoord == storageElt.ycoord})
			{
				int counter = 1;
				while(true)
				{
					if(Box.findByDescription(box.description+"-"+counter))
					{
						def newBox = Box.findByDescription(box.description+"-"+counter)
						if(!newBox.elements.find{it.xcoord == storageElt.xcoord && it.ycoord == storageElt.ycoord})
						{
							box = newBox
							break;
						}  
						counter++
					}
					else
					{
						def newBox = new Box(description: (box.description+"-"+counter), xdim: 8, ydim: 12)
						box.compartment.addToBoxes(newBox).save(flush:true)
						
						box = newBox
						break;
					}
				}
			}
			
			if(box) box.addToElements(storageElt)?.save(flush:true)
			else
			{
				log.warn "No box found for description ${description}"
			}
			if(box?.hasErrors())
			{
				box.errors.each{
					log.error it.toString()
				}
			}
			else return
		}
	}
	
	def createStorageElement(def storage, def dataObj)
	{
		def newElement = new StorageElement(description: "", xcoord: boxCreationService.getIntFromAlpha(storage.oldX.trim().charAt(0))-1,
		ycoord: storage.oldY.round()-1, dataObj: dataObj)
		
		if(newElement.hasErrors())
		{
			newElement.errors.each{
				log.error it.toString()
			}	
		}
		
		if(!newElement)
		{
			log.error "could not create storage element for ${storage} and ${dataObj}"
		}
		
		return newElement
	}
	
	def findStorage(def storageId)
	{
		def storageLocs = importer.getStorageLocations()
		
		if(!storageId)
		{
			 log.warn "could not freeze dataobject. no storageId given"; return;
		}
		
		//find storage
		def storage = storageLocs.find{it.oldId == storageId}
		
		if (!storage)
		{
			log.warn "could not find corresponding storage entry for id ${storageId}"; return;
		}
		
		if (!storage.oldX || !storage.oldY || (storage.oldX.trim().size() == 0))
		{
			log.warn "coordinates missing for id ${storageId}"; return;
		}
		
		return storage
	}
	
	def freezeDataObjectWithData(DataObject dataObj, def storageId)
	{
		def storageLocs = importer.getStorageLocations()
		
		def storage = findStorage(storageId)
		if(!storage) return
		
		def compartment
		
		if(!storage.compartmentId)
		{
			def importFreezer = Freezer.findByDescription("import")
			compartment = Compartment.findByStorageTypeAndDescription(importFreezer, "import")
			if(!compartment){
				compartment = new Compartment(description: "import")
				importFreezer.addToCompartments(compartment).save()
			}
			
		}
		else
		{
			def compartmentId = getCompartments().find{it.oldId == storage.compartmentId}.newId
			if(!compartmentId)
			{
				log.warn "could not find corresponding compartment for id ${compartmentId}"; return;
			}
			compartment = Compartment.get(compartmentId)
		}
		
		//create trouble maker box (one single box that does not fit 96well size)
		def newBox = new Box(xdim: 1, ydim: 20, description: "Odense-1", compartment:
			Compartment.get(getCompartments().find{it.oldId == "7CEF1741-EF49-4F96-AA1E-DBBE2A5488C4"}.newId))
		compartment.addToBoxes(newBox).save(flush:true)
		
		//find box
		def box = findBox(storage, compartment)
		
		//store dataobject
		box.addToElements(new StorageElement(description: "", xcoord: boxCreationService.getIntFromAlpha(storage.oldX.charAt(0)),
			ycoord: storage.oldY.round(), dataObj: dataObj)).save(flush:true)
			
		if(box.hasErrors())
		{
			box.errors.each{
				log.error it.toString()
			}
		}
	}
	
	def findBox(def storage, def compartment)
	{
		def box
		
		if(!storage?.boxName)
		{
			box = Box.findByCompartmentAndDescription(compartment, "import")
			
			if(!box) box = new Box(xdim: 8, ydim: 12, description: "import", compartment: compartment).save()
		}
		else
		{
			box = Box.findByCompartmentAndDescription(compartment, storage.boxName.toString().trim())
			if(!box)
			{
				box = new Box(xdim: 8, ydim: 12, description: storage.boxName.toString().trim(), compartment: compartment).save(flush:true)
			}
		}
		
		return box
	}

}
