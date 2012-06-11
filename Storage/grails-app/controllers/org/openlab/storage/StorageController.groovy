package org.openlab.storage

import grails.converters.*
import groovy.xml.MarkupBuilder;
import org.openlab.data.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class StorageController extends DataTableControllerTemplate {

	def index = { redirect(action: list) }

	def createTreeData()
	{
			//prepare XML
			def writer = new StringWriter() 
	    	def xml = new MarkupBuilder(writer)
			def storageTypes = StorageType.list().sort{it.toString()}.reverse()
			
			//build XML output
	    	xml.root(name: "Storage", checked: false, expanded: true)
		    {
		    	storageTypes.each{storage ->
		    		storageType(id: storage.id, name: storage.description)
		    		{
		    			convertCompartmentsToXML(xml, storage.compartments)
		    		}
		    	}
		    }
			return writer
	}
	
	def list = {	
	    	model: [ "treedata": createTreeData().toString(), "controller": "box", "action": "showBox"]
	}
	
	def edit = {
		def storageTypeList = StorageType.list()
		def firstStorageType = null
		if(storageTypeList.size() > 0) firstStorageType = storageTypeList.get(0)
		def compartmentList = Compartment.list().findAll{it.storageType == firstStorageType}.collect{it.description}
		
		[storageTypeList: storageTypeList, compartmentList: compartmentList]
	}
	
	def updateCompartmentSelect = {
		println params.selectedValue
		
		def storageType = StorageType.findByDescription(params.selectedValue)
		def selectedCompartments = Compartment.findAllByStorageType(storageType).collect{it.description}
		
		render(template:"/layouts/compartmentSelect", plugin:"storage", model: [compartmentList: selectedCompartments])
	}
	
	def updateBoxList = {
		def storageType = StorageType.findByDescription(params.storageType)
		def compartment = Compartment.findByStorageTypeAndDescription(storageType, params.compartment)
		if(!compartment) render ""
		else render(template:"/layouts/listBoxes", plugin:"storage", model: [storageType: storageType, compartment: compartment, compartmentId: compartment?.id])
	}
	
	
	/**
	* List boxes belonging to a compartment as JSON
	*/
   def listBoxesAsJSON = {
	
	   def compInstance = Compartment.get(params.id)
	   
	   def boxes = Box.findAllByCompartment(compInstance)
		  
	   def list = []
	   boxes.each{
		   list << [
			   id: it.id,
			   description: it.description,
			   xdim : it.xdim,
			   ydim : it.ydim,
			   lastUpdate : grailsUITagLibService.dateToJs(it.lastUpdate),
			   modifyUrls: remoteLink(action:'removeTableRow', 
				   before:"if(!confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}')) return false;", controller:'storage', id: it.id, onSuccess: "javascript:GRAILSUI.dtBoxes.requery();"){"<img src=${createLinkTo(dir:'images/skin',file:'olf_delete.png')} alt='Delete' />"}
		   ]
	   }
	   render tableDataAsJSON(jsonList: list)
   }
   
   def removeCompartment = {
	   Compartment.get(params.id).delete()
	   redirect(action: "edit", params: [bodyOnly: true])
   }
   
   def tableDataChange = {
   	        def box = Box.get(params.id)
			if(box.elements.size()>0 && ((params.field == 'xdim') || (params.field== 'ydim')))
			{
				 render text: "Size of a box can only be changed when box is empty!", status: 503 
			}
			else {
				
				if((params.field == 'xdim') || (params.field == 'ydim'))
				{
					try{
						int number = Integer.valueOf(params.newValue)
						box."$params.field" = number
					}catch(NumberFormatException e)
					{
						box."$params.field" = 10
					}
				}
				else
					box."$params.field" = params.newValue
			
				if(box.save(flush:true)) render ""
			
				else render text : "Could not save:${g.renderErrors()}", status: 503
			} 
	}
   
    def addTableRow = {
	   
		def xdim = settingsService.getDefaultSetting(key:"storage.xdim")?:10
		def ydim = settingsService.getDefaultSetting(key:"storage.ydim")?:10
		def compartment = Compartment.get(params.id)
		
		def newBox = new Box(xdim: xdim, ydim: ydim, description: "(unnamed probebox)", compartment: compartment)
		newBox.save()
		if(Box.findByDescriptionAndCompartment("(unnamed probebox)", compartment))
		{
			newBox.description += (":" +newBox.id)
		}
		newBox.save(flush:true)
		
		compartment.boxes.add(newBox)
		compartment.save()
		
		flash.message = "Probebox added to ${compartment.description}"

		render "success"
	}
   
    def removeTableRow =  {
	   def box = Box.get(params.id)
	   def boxDescription = box.description
	   box.delete()
	   flash.message = "Probebox ${boxDescription} deleted"
	   render "success"
	}
	
	
	def selectTree = {
	    	model: [ "treedata": createTreeData().toString(), "controller": "box", "action": "showBox" ]
	}
	
	def convertCompartmentsToXML(builder, compartments)
	{
		compartments.toList().sort{it.toString()}.each{comp ->
			
			builder.compartment(id: comp.id, name: comp.description)
			{
				comp.boxes.toList().sort{it.toString()}.each{b ->
					box(id: b.id, name: b.description)
				}
			}
		}
	}
    
	def showTree = {
			render(plugin: 'storage', template:'/layouts/storageTree', model: ["treedata": createTreeData().toString(), "dataObjId": params.id, "treeInTab": params.treeInTab, "controller": "box", "action": "showBox"])
	}
	
	def storageExportService
	
	def exportHierarchy = {
		
		response.contentType = ConfigurationHolder.config.grails.mime.types["excel"]
		response.setHeader("Content-disposition", "attachment; filename=Storage.xls")
		
		storageExportService.export(response)
	}
}
