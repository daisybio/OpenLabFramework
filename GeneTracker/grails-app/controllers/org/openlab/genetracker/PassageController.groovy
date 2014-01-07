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
 * DataTableController for _passageTab.gsp
 * @author markus.list
 *
 */
class PassageController{

	def springSecurityService
	def settingsService
	
	def scaffold = Passage

	def show = {
		redirect(controller: "cellLineData", action: "show", id: Passage.get(params.id).cellLineData.id, params: params)
	}

    def addPassageInCellLineDataTab = {
        params.notes = ""
        params.researcher = springSecurityService.currentUser

        if(!new Passage(params).save(flush: true, failOnError: true)){
            response.sendError(404)
        }
        else{
            def model = new org.openlab.module.tab.PassageModule().getModelForDomainClass("cellLineData", params["cellLineData.id"])
            render(template: "/tabs/passageTab", model: model)
        }
    }

    def delete = {
        def passageInstance = Passage.get(params.id)
        def cellLineDataId = passageInstance.cellLineDataId

        if (!passageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'passage.label', default: 'Passage'), params.id])
            response.sendError(404)
        }

        try {
            passageInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'passage.label', default: 'Passage'), params.id])

            def model = new org.openlab.module.tab.PassageModule().getModelForDomainClass("cellLineData", cellLineDataId)
            render(template: "/tabs/passageTab", model: model)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'passage.label', default: 'Passage'), params.id])
            response.sendError(404)
        }
    }
	
}
