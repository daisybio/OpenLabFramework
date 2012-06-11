package org.openlab.storage

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.openlab.main.*;

/**
 * Handles operations concerning the box. A box is 
 * the smallest compartment in the storage concept.
 * It contains StorageElements that are linked to 
 * coordinates of the box as well as DataObjects.
 * @author markus.list
 *
 */
class BoxController {

    def scaffold = Box
    
    /**
     * Return a model of the given box id
     */
    def showBox = {
    	
    	def id = params.id
    		
    	if(params.id instanceof String)
    	{
    		id = Integer.valueOf(params.id)
    		[box: Box.get(id)]
    	}
    		
    	[box: Box.get(id)]
    }
    
    /**
     * Add the DataObject (params.id) to the cell at the given box and coordinates
     */
    def addDataObject = 
    {
    		//check for existing elements in same box at same coordinates 
    		//TODO use a nicer sql statement to do this
    		def oldElts = StorageElement.findAllByXcoordAndYcoord(params.x, params.y)
    		oldElts.each{
    			if (it.box.id == params.boxId) render "There is already an element at these coordinates!"
    		}
    		def storageElt = new StorageElement(description: "", xcoord: params.x, ycoord: params.y, dataObj: DataObject.get(params.id))
    		Box.get(params.boxId).addToElements(storageElt).save(flush:true)
    		
			render g.updateBoxTable(id: params.boxId, addToCell: true)
    }
    
    /**
     * Removes the DataObject (params.id) from the cell at the given box and coordinates
     */
    def removeDataObject =
    {
		def box = Box.findById(params.boxId)
		def storageElt = box.elements.find{it.xcoord.toString() == params.x && it.ycoord.toString() == params.y}
    		
		//need to remove elt from box, otherwise exception: elt would be re-saved by cascade
		box.removeFromElements(storageElt).save(flush:true)
		
		render g.updateBoxTable(id: params.boxId, addToCell:true)
    }
    
    /**
     * Renders how many elements of an entity are left
     */
    def numberOfEntitiesLeft =
    {
    		render g.numberOfEntitiesLeft()
    }
    
    /**
     * Renders the label for the current box
     */
    def currentBox =
    {
    	def box = Box.get(params.boxId)
		render g.showCurrentBox(box: box)
    }
    
    /**
     * Render a box for a tab
     */
    def showBoxInTab =
    {
    		render g.updateBoxTable(id: params.boxId, addToCell: true)
    }
	
	def boxCreationService
	def boxExportService
	
	def export = {
		
		response.contentType = ConfigurationHolder.config.grails.mime.types["excel"]
		response.setHeader("Content-disposition", "attachment; filename=${Box.get(params.id).toString().replace(' - ','\\_')}.xls")
		
		boxExportService.export(params.id, response)		
    }

}
