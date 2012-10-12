package org.openlab.genetracker

import org.springframework.dao.DataIntegrityViolationException
import org.openlab.module.tab.AntibioticsModule

class AntibioticsWithConcentrationController {

    def scaffold = true

    def addAntibioticsInTab(){

        new AntibioticsWithConcentration(params).save(flush:true, failOnError: true)

        def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))

        render(template: "/tabs/antibioticsTab", model: newModel)
    }

    def delete() {
        def antibioticsInstance = AntibioticsWithConcentration.get(params.long("id"))

        if (!antibioticsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'antibioticsWithConcentration.label', default: 'Antibiotics'), params.id])
            def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))
            render(template: "/tabs/antibioticsTab", model: newModel)
            return
        }

        try {
            antibioticsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'antibioticsWithConcentration.label', default: 'Antibiotics'), params.id])
            def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))
            render(template: "/tabs/antibioticsTab", model: newModel)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'antibioticsWithConcentration.label', default: 'Antibiotics'), params.id])
            def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))
            render(template: "/tabs/antibioticsTab", model: newModel)
        }
    }
}
