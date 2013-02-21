package org.openlab.storage

import grails.converters.*
import groovy.xml.MarkupBuilder;
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.openlab.main.Project

class StorageController {

	def index = { redirect(action: list) }

    def storageTabForPassage()
    {
        render template: "/tabs/storage", plugin: "storage"
    }

    def storageTabWithSubDataObj(){
        render template: "/tabs/cellLineDataStorage", plugin: "storage", params: params
    }

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

    def removeWithMobile = {
        StorageElement.get(params.id).delete()
        render "<div class='message'>Object successfully removed from storage</div>"
    }
	
	def edit = {
		def storageTypeList = StorageType.list()
		def firstStorageType = null
		if(storageTypeList.size() > 0) firstStorageType = storageTypeList.get(0)
		def compartmentList = Compartment.findAllWhere(storageType: firstStorageType)
		
		[storageTypeList: storageTypeList, compartmentList: compartmentList]
	}
	
	def updateCompartmentSelect = {

		def storageType = StorageType.findByDescription(params.selectedValue)
		def selectedCompartments = Compartment.findAllByStorageType(storageType)
		
		render(template:"/layouts/compartmentSelect", plugin:"storage", model: [compartmentList: selectedCompartments])
	}
	
	def updateBoxList = {
		def storageType = StorageType.findByDescription(params.storageType)
		def compartment = Compartment.findByStorageTypeAndDescription(storageType, params.compartment)
		if(!compartment) render "Compartment not found."
		else render(template:"/layouts/listBoxes", plugin:"storage", model: [storageType: storageType, compartment: compartment, compartmentId: compartment?.id])
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
    
    def treeDataAsJSON = {
        if(params.nodeType == "root")
        {
            def locationsAsJSON = StorageLocation.list(sort: "description").collect{
                [
                        "data" : it.description,
                        "attr" : [ "id" : it.id , "rel":"location", "nodeType" : "location"],
                        "state" : "closed"
                ]
            }
            render locationsAsJSON as JSON
        }

        else if(params.nodeType == "location") {
            def storageTypes = StorageType.findAllByLocation(StorageLocation.get(params.id))

            def storageTypesAsJSON = storageTypes.sort{it.description}.collect{
                [
                        "data" : it.description,
                        "attr" : [ "id" : it.id , "rel":"storageType", "nodeType" : "storageType"],
                        "state" : "closed"
                ]
            }
            render storageTypesAsJSON as JSON
        }

        else if(params.nodeType == "storageType"){
            def compartmentsAsJSON = Compartment.findAllByStorageType(StorageType.get(params.id)).sort{it.description}.collect{
                [
                        "data" : it.description,
                        "attr" : [ "id" : it.id , "rel":"compartment", "nodeType" : "compartment"],
                        "state": "closed"
                ]
            }

            render compartmentsAsJSON as JSON
        }
        else if(params.nodeType == "compartment"){
            def boxesAsJSON = Box.findAllByCompartment(Compartment.get(params.id)).sort{it.description}.collect{
                [
                        "data" : it.description,
                        "attr" : [ "id" : it.id , "rel":"box", "nodeType" : "box"]
                ]
            }
            render boxesAsJSON as JSON
        }
    }

    def showTree = {
        render template: "/layouts/storageTree"
    }
	
	def storageExportService
	
	def exportHierarchy = {
		
		response.contentType = ConfigurationHolder.config.grails.mime.types["excel"]
		response.setHeader("Content-disposition", "attachment; filename=Storage.xls")
		
		storageExportService.export(response)
	}
}
