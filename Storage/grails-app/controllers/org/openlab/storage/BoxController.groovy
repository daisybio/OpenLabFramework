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
    		def existsAlready = StorageElement.findWhere(xcoord: params.int("x"), ycoord: params.int("y"), box: Box.get(params.long("boxId")))
            if(existsAlready) render "There is already an element at these coordinates!"

            else{
                def storageElt = new StorageElement(description: "", xcoord: params.int("x"), ycoord: params.int("y"), dataObj: DataObject.get(params.id), subDataObj: params.subDataObj?SubDataObject.get(params.long("subDataObj")):null)
    		    println storageElt
                Box.get(params.long("boxId")).addToElements(storageElt).save(flush:true, failOnError: true)
    		
			    render g.updateBoxTable(id: params.boxId, addToCell: true)
            }
    }
    
    /**
     * Removes the DataObject (params.id) from the cell at the given box and coordinates
     */
    def removeDataObject =
    {
		def box = Box.get(params.long("boxId"))
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
