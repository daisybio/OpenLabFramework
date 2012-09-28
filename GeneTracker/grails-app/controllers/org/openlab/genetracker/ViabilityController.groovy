package org.openlab.genetracker

import java.util.Date;
import org.openlab.security.User;
import grails.converters.*;
import org.openlab.data.*;
import org.openlab.genetracker.*
import org.springframework.dao.DataIntegrityViolationException;

/**
 * DataTableController for _viabilityTab.gsp
 * @author markus.list
 *
 */
class ViabilityController{

    def springSecurityService

	def scaffold = Viability

	def show = {
		redirect(controller: "cellLineData", action: "show", id: Viability.get(params.id).cellLineData.id, params: [bodyOnly:true])
	}

    def addViabilityInGeneTab = {
        params.notes = ""
        params.researcher = springSecurityService.currentUser

        if(!new Viability(params).save(flush: true, failOnError: true)){
            response.sendError(404)
        }
        else{
            def model = new org.openlab.module.tab.ViabilityModule().getModelForDomainClass("gene", params["gene.id"])
            println model
            render(template: "/tabs/viabilityTab", model: model)
        }
    }
    
    def delete = {
        def viabilityInstance = Viability.get(params.id)
        def gene = viabilityInstance.geneId

        if (!viabilityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'viability.label', default: 'Viability'), params.id])
            response.sendError(404)
        }

        try {
            viabilityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'viability.label', default: 'Viability'), params.id])

            def model = new org.openlab.module.tab.ViabilityModule().getModelForDomainClass("gene", gene)
            render(template: "/tabs/viabilityTab", model: model)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'viability.label', default: 'Viability'), params.id])
            response.sendError(404)
        }
    }
}
