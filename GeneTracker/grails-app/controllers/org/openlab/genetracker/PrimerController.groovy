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
