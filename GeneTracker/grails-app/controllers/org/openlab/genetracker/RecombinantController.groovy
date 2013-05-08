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

import org.openlab.genetracker.vector.*;
import org.springframework.dao.DataIntegrityViolationException;

class RecombinantController{

	def scaffold = Recombinant

    def addRecombinantInTab(){

        new Recombinant(vector: Vector.get(params.vector), notes: '', genes: [Gene.get(params.gene)]).save(flush:true, failOnError: true)

        def gene = Gene.get(params.gene)
        def gvectors = Recombinant.withCriteria{
            genes{
                eq("id", Long.valueOf(params.gene))
            }
        }
        def vectors = Vector.where{ type != "Acceptor"}.list(sort: "label")

        def newModel = [gene: gene, geneVectorList: gvectors, vectors: vectors]

        render(template: "/tabs/geneVectorTab", model: newModel)
    }

    def delete() {
        def recombinantInstance = Recombinant.get(params.id)
        if (!recombinantInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recombinant.label', default: 'Recombinant'), params.id])
            redirect(action: "show", params: [bodyOnly: params.bodyOnly?:false])
            return
        }

        else if(CellLineData.findByFirstRecombinant(recombinantInstance) || CellLineData.findBySecondRecombinant(recombinantInstance))
        {
            flash.message = "Recombinant cannot be deleted as long as it is part of a CellLineData object."
            redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
            return
        }

        try {
            recombinantInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recombinant.label', default: 'Recombinant'), params.id])
            redirect(action: "list", params: [bodyOnly: params.bodyOnly?:false])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recombinant.label', default: 'Recombinant'), params.id])
            redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
        }
    }
}
