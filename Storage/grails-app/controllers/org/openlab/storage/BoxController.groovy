package org.openlab.storage

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.openlab.main.*
import org.springframework.dao.DataIntegrityViolationException;

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

    def listBoxesInCompartment = {

        def boxes = Box.findAllByCompartment(Compartment.get(params.id))

        [boxInstanceList: boxes, boxInstanceTotal: boxes.size()]
    }
    
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

    def addBox = {

        if(!new Box(params).save(flush:true, failOnError:true))
        {
            flash.message = "Could not create box"
        }
        render(template: "/layouts/listBoxes", model: [compartmentId: params["compartment.id"]])
    }
    
    def deleteBox = {

        def boxInstance = Box.get(params.id)
        def compartment = boxInstance.compartmentId

        if (!boxInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'box.label', default: 'Box'), params.id])
            render(template: "/layouts/listBoxes", model:[compartmentId: compartment])
        }

        try {
            boxInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'box.label', default: 'Box'), params.id])

            render(template: "/layouts/listBoxes", model:[compartmentId: compartment])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'box.label', default: 'Box'), params.id])
            render(template: "/layouts/listBoxes", model:[compartmentId: compartment])
        }
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
