package org.openlab.content

import org.openlab.module.Module

class TabsController {

    def grailsApplication

    def renderTab = {
        log.debug params
        Module module = grailsApplication.getArtefact("Module", params.module).referenceInstance

        def model = module.getModelForDomainClass(params.domainClass, params.id)

        render(template: params.template, plugin: params.plugin, model: model)
    }
}
