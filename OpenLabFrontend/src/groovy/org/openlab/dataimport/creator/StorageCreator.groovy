package org.openlab.dataimport.creator

import org.openlab.security.*;
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
import groovy.transform.InheritConstructors

@InheritConstructors
class StorageCreator extends GeneTrackerImportClass{

	def createCompartmentsFromData()
	{
		log.info "creating compartments..."
		
		def freezers = importer.getFreezers()
		
		importer.getCompartments().each{comp ->
			def freezerId = freezers.find{it.oldId == comp.freezerId}?.newId
			
			def freezer
			
			if(!freezerId)
			{
				 log.warn "could not map freezer ids";
				 
				 def importFreezer = Freezer.findByDescription("import")
				 if(!importFreezer)
					 importFreezer = new Freezer(description:"import", temp: "0",
						 location: new StorageLocation(description: "import").save()).save()
				 
				 freezer = importFreezer
			}
			else freezer = Freezer.get(freezerId)
			
			if(!comp.description) comp.description = "import"
			
			def newCompartment = new Compartment(comp)
			
			freezer.addToCompartments(newCompartment).save(flush:true)
			
			if(freezer.hasErrors())
			{
				freezer.errors.each{
					log.error it.toString()
				}
			}
			else comp.newId = newCompartment.id

		}
	}
	
	def createStorageManually()
	{
		/*Odense*/
		
		def location = new StorageLocation(description: "WP25", room: 47).save()
		def compartment = new Compartment(description: "Freezer(-80)")
		def box = new Box(xdim: 8, ydim: 12, description: "Gateway-1")
		new Freezer(temperature: -80, location: location, description: "Odense")
			.addToCompartments(compartment
				.addToBoxes(box)).save()
				
		/*Heidelberg*/
				
		def xdim = 8
		def ydim = 12
				
		//-80
		compartment = new Compartment(description: "2.325 (-80)")
		location = new StorageLocation(description: "Heidelberg", room: 2325).save()
		new Freezer(temperature: -80, location: location, description: "Heidelberg -80")
			.addToCompartments(compartment).save()
		
		for(int i = 1; i <= 11; i++)
			compartment.addToBoxes(new Box(xdim: xdim, ydim: ydim, description: "Rainer${i}")).save()
			
		compartment.addToBoxes(new Box(xdim: xdim, ydim: ydim, description: "Paul")).save()
		
		compartment.addToBoxes(new Box(xdim: xdim, ydim: ydim, description: "RZPD strain plate")).save()
		
		compartment.addToBoxes(new Box(xdim: xdim, ydim: ydim, description: "MGC_Klone")).save()
			
		//Stephi
		compartment= new Compartment(description: "2.312 (Stephi)")
		location = new StorageLocation(description: "Heidelberg", room: 2312).save()
		new Freezer(temperature: 0, location: location, description: "Heidelberg (Stephi)")
			.addToCompartments(compartment).save()
			
		//for(int i = 1; i < 4; i++)
			compartment.addToBoxes(new Box(xdim: xdim, ydim: ydim, description: "DNA")).save()
		
		//-20
		compartment = new Compartment(description: "2325 (-20)")
		location = new StorageLocation(description: "Heidelberg", room: 2325).save()
		new Freezer(temperature: -20, location: location, description: "Heidelberg -20")
			.addToCompartments(compartment).save()
			
		//for(int i = 0; i < 3; i++)
			compartment.addToBoxes(new Box(xdim: xdim, ydim: ydim, description: "Primer")).save()
	}
	
	def createFreezersFromData()
	{
		log.info "creating freezers..."
		
		importer.getFreezers().each{freezer ->
			
			//create location
			freezer.location = new StorageLocation(description: (freezer.oldLocation?:freezer.description)?:freezer.oldId).save()
			
			def newFreezer = new Freezer(freezer).save()
			if(newFreezer.hasErrors())
			{
				newFreezer.errors.each{
					log.error it.toString()
				}
			}
			else
			{
				freezer.newId = newFreezer.id
			}
		}
	}

	
}
