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

import org.springframework.dao.DataIntegrityViolationException

class PrimerController {

	def scaffold = Primer

    def addPrimerInGeneTab = {

        if(!new Primer(params).save(flush: true, failOnError: true)){
            response.sendError(404)
        }
        else{
            def model = new org.openlab.module.tab.PrimerModule().getModelForDomainClass("gene", params["gene.id"])
            render(template: "/tabs/primerTab", model: model)
        }
    }

    def delete = {
        def primerInstance = Primer.get(params.id)
        def geneId = primerInstance.geneId

        if (!primerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'primer.label', default: 'Primer'), params.id])
            response.sendError(404)
        }

        try {
            primerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'primer.label', default: 'Primer'), params.id])

            def model = new org.openlab.module.tab.PrimerModule().getModelForDomainClass("gene", geneId)
            render(template: "/tabs/primerTab", model: model)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'primer.label', default: 'Primer'), params.id])
            response.sendError(404)
        }
    }
}
