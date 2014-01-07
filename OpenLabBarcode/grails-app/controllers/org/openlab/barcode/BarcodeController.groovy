/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.openlab.barcode

import org.openlab.main.*;
import org.openlab.storage.*;

class BarcodeController {

	def labelPrinterService
	def barcodeBuilderService
	def grailsApplication
	def settingsService
	def springSecurityService
	
	def scaffold = Barcode
	
	def printOperation = {
		print(new Barcode(params))
	}
	
	/**
	 *  action that brings up the barcode scanner interface
	 */
	def simpleShow = {
		[:]
	}
	
	def scannerAddin = {
		render(template: "/addins/barcodeCreation", plugin: "open-lab-barcode", model: [switched:true])
	}
	
	/**
	 *  action called when a barcode is submitted. renders information for display
	 */
	def ajaxShow = {
		int length
		
		try{
			length = Integer.valueOf(settingsService.getDefaultSetting(key: "barcode.barcodelength")?:16)
		}catch(NumberFormatException e)
		{
			length = 16
		}
		
		if(params.id.toString().length() < length)
			return render("<span class='message'>Input to short (needed: ${length}, given: ${params.id.length()})</span>")  		
		
		def barcode
		
		try{
			barcode = barcodeBuilderService.buildBarcodeFromId(params.id)
			flash.message = "Scanned: " + barcode.text + " (" + barcode.description + ")"
		}catch(Exception e){
			log.error e.getMessage()
		}
		if(barcode && params.bodyOnly)
		{
			redirect(controller: "dataObject", action:"showSubClass", id: barcode.dataObject.id)
		}
		else if(barcode)
		{
			[dataObject: barcode.dataObject, storageElements: StorageElement.findAllByDataObj(barcode.dataObject)]
		}
		else
		{
			return render("<span class='message'>Could not process input. Input has either wrong format or entry does not exist.</span>")
		}
	}
	
	def print(def barcode) 
	{
		def posResponse
		try{
			def printerParams = barcodeBuilderService.getParamsFromBarcode(barcode)
			println "printerParams: " + printerParams
			posResponse = labelPrinterService.sendToLabelPrinter(printerParams)
		}catch(Exception e){
			log.error ("Error during Label Print:"+e.getMessage()+ e.getStackTrace())
			posResponse = null
		}
		
		if(!posResponse)
		{
			render(text: "<span class='message'>Label could not be printed!</span>")
		}
		else 
			render (text: "<span class='message'>Label successfully printed!</span>")
	}
	
	/**
	 * Called upon render of barcode label addin. Tries to determine whether barcode labels
	 * are supported for the current view and renders the view corresponding to whether a
	 * barcode object already exists or not.
	 */
	def renderBarcodeCreator = {
		def className = null
        def barcodeDataObject

        if (params.dataObject) params.id = params["dataObject.id"]
        if (params.barcodeDataObject) barcodeDataObject = BarcodeDataObject.get(params["barcodeDataObject.id"])

		if(params.className)
		{
			if(params.className.toString().startsWith("class"))
			{
				barcodeDataObject = BarcodeDataObject.findByFullName(params.className.substring(6))
			}
			else
			{
				barcodeDataObject = BarcodeDataObject.findByShortName(params.className)
			} 
		}
		
		if(barcodeDataObject)
			className = barcodeDataObject.fullName
			
		if(!barcodeDataObject)
		{
			render(template: "/addins/barcodeCreation", plugin: "open-lab-barcode")
		}
		else
		{
			if(params.id && className && (params.id != "undefined") && (className != "undefined") && (className != ""))
			{
				def classInstance = grailsApplication.getDomainClass(className).newInstance()
				def objectInstance = classInstance.get(params.id)
				
				def barcode
				def model = [:]
				def template = "/layouts/barcodeCreate"

				if(objectInstance instanceof org.openlab.genetracker.CellLineData)
				{					
					model.passages = org.openlab.genetracker.Passage.findAllByCellLineData(objectInstance)
					return render(template: "/layouts/barcodeForPassage", plugin: "open-lab-barcode", model: model)
				}
				
				else if(objectInstance instanceof DataObject)
				{
					barcode = Barcode.findByDataObject(objectInstance)
				}

				if(barcode)
					render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: (model += [update: true, barcodeDataObject: barcodeDataObject, dataObject: objectInstance, barcodeInstance: barcode]))
				else
				render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: (model += [update: false, barcodeDataObject: barcodeDataObject, dataObject: objectInstance, barcodeInstance: barcode]))
			}
			else render(template: "/addins/barcodeCreation", plugin: "open-lab-barcode")
		}
	}
	
	/**
	 * Allows for the creation of a barcode after selection of a passage
	 */
	def createBarcodeWithPassage = {
		
		def barcode
		
		def subDataObjectInstance = org.openlab.genetracker.Passage.findById(params.passageSelect)
		def objectInstance = subDataObjectInstance.cellLineData
		
		if(subDataObjectInstance)
		{
			barcode = Barcode.findBySubDataObject(subDataObjectInstance)
		}
		
		//set the model up in such way that there is only the selected passage available
		def model = [:]
		model.passages = [subDataObjectInstance]
		
		//although this is about passages we want the barcode be subject of cellLineData because this is the displayable instance
		def barcodeDataObject = BarcodeDataObject.findByShortName("cellLineData")
		
		if(barcode)
		{
			render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: (model += [update: true, subDataObject: subDataObjectInstance, barcodeDataObject: barcodeDataObject, dataObject: objectInstance, barcodeInstance: barcode]))
		}
		else
		{
			render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: (model += [update: false, subDataObject: subDataObjectInstance, barcodeDataObject: barcodeDataObject, dataObject: objectInstance, barcodeInstance: barcode]))
		}
	}
	
	/**
	 * A new Barcode object is created and the print process is started
	 */
	def saveAndPrint = {
		
		def newBarcode = new Barcode(params)
		
		if(newBarcode.id) return updateAndPrint()
		
		if(newBarcode.hasErrors()) 
			render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: [update: false, barcodeInstance: newBarcode])
			
		else
		{
			newBarcode.save(flush:true)
			//print(newBarcode)
			//render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: [update: true, barcodeInstance: barcode])
			renderPreview(newBarcode)
		}
	}
	
	def renderPreview(def barcode)
	{
		render(template: "/layouts/barcodePreview", plugin: "open-lab-barcode", 
			model: [barcodeInstance:barcode, barcodeLabel:barcode.label.xml.replace("\n", ""), barcodeText: barcodeBuilderService.buildBarcodeId(barcode)])
	}
	
	/**
	 * An existing barcode object is updated and the print process is started
	 */
	def updateAndPrint = {
		def barcode = Barcode.get(params.id)
		
		barcode.properties = params
		
		if(!barcode.hasErrors())
		{	
			barcode.save(flush:true)
			renderPreview(barcode)		}
		else
		{
			render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: [update: true, barcodeInstance: newBarcode])
		}
	}
	
	def backToEdit = {
		def barcode = Barcode.get(params.id)
		
		if(barcode)
			render(template: "/layouts/barcodeCreate", plugin: "open-lab-barcode", model: [update: true, barcodeDataObject: barcode.barcodeDataObject, dataObject: barcode.dataObject, barcodeInstance: barcode])
		
	}
}
