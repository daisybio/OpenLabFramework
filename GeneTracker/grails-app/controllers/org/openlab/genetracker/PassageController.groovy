package org.openlab.genetracker

import grails.converters.*;
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
