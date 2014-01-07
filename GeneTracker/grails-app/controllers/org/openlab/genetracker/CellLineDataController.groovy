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
package org.openlab.genetracker

import org.springframework.dao.DataIntegrityViolationException;

/**
 * DataTableController for _cellLineDataTab.gsp and 
 * scaffolded controller for CRUD actions on CellLineData 
 * @author markus.list
 *
 */
class CellLineDataController{

	def scaffold = CellLineData
	def RecombinantsService
	
	/**
	 * Copies elements from a persistentSet into a new list
	 * @param oldList
	 * @return
	 */
	def persistentCollectionCopy(def oldList){
		def newList = []
		
        oldList.each{
			newList << it
		}
		
		return newList
	}
	
	/**
	 * override save method so that CellLine's default values for
	 * goodies, antibiotics etc. can be added
	 */
    def save = {  	
			
			def cellLine = CellLine.get(params.cellLine.id)
			/**
			 * add any existing cellLine properties to params if non given
			 */
			if(cellLine.mediumAdditives)
				params.mediumAdditives = persistentCollectionCopy(cellLine.mediumAdditives)

			
			if((params.cultureMedia?.id.toString() == "null") && cellLine.cultureMedia)
				params.cultureMedia = cellLine.cultureMedia
	    	
			/**
			 * usual save as in scaffolded controller
			 */
			def cellLineData = new CellLineData(params)
	        if (cellLineData.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'CellLineData.label', default: 'CellLineData'), cellLineData.id])}"
	            redirect(action: "show", id: cellLineData.id, params: params)
	        }
	        else {
	        	render(view: "create", model: [cellLineDataInstance: cellLineData])
	        }
	    }

    def delete() {
        def cellLineDataInstance = CellLineData.get(params.id)
        if (!cellLineDataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'CellLineData.delete.label', default: 'CellLineData'), params.id])
            redirect(action: "list", params: [bodyOnly: params.bodyOnly?:false])
            return
        }

        try {
            if(cellLineDataInstance.mediumAdditives) cellLineDataInstance.mediumAdditives.clear()
            Passage.findAllByCellLineData(cellLineDataInstance).each{it.delete(flush: true)}
            AntibioticsWithConcentration.findAllByCellLineData(cellLineDataInstance).each{it.delete(flush:true)}

            cellLineDataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'CellLineData.delete.label', default: 'CellLineData'), params.id])
            redirect(action: "list", params: [bodyOnly: params.bodyOnly?:false])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'CellLineData.delete.label', default: 'CellLineData'), params.id])
            redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
        }
    }
	
	def updateFirstVector = {
		if(params.firstGene)
		{
			def recombinants = Recombinant.withCriteria{
				createAlias("genes", "g")
				and{
					eq("g.id", Long.valueOf(params.firstGene))
				
					vector 
					{
						eq("type", "Integration (First)")
					}
				}
			}
			
			//old and slow
			//def recombinants = Recombinant.list().findAll{it.genes?.contains(Gene.get(params.firstGene)) && (it.vector.type == 'Integration (First)')}
	
			if(recombinants)
				render g.select(name: "firstRecombinant.id", from: recombinants, optionKey: "id")
			else render "No vector has been combined with this ${remoteLink(controller:"gene", action:"show", update:"body", id: params.firstGene){"gene"}} yet."
		}
		else render "Select a gene."
	}
	
	def updateSecondVector = {
		if(params.secondGene)
		{
			def recombinants = Recombinant.withCriteria{
				createAlias("genes", "g")
				eq("g.id", Long.valueOf(params.secondGene))
				
				vector {
					eq("type", "Integration (Second)")
				}
			}
			
			//old and slow
			//def recombinants = Recombinant.list().findAll{it.genes?.contains(Gene.get(params.secondGene)) && (it.vector.type == 'Integration (Second)')}
			
			if(recombinants)
				render g.select(name: "secondRecombinant.id", from: recombinants, optionKey: "id")
			else render "No vector has been combined with this ${remoteLink(controller:"gene", action:"show", update:"body", id: params.secondGene){"gene"}} yet."
		}
		else render "Select a gene."
	}
}
