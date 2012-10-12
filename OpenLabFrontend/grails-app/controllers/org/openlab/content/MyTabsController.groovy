package org.openlab.content

import org.openlab.module.Module

class MyTabsController {

    def grailsApplication

    def renderTab(){

        log.debug params
        Module module = grailsApplication.getArtefact("Module", params.module).referenceInstance

        def model = module.getModelForDomainClass(params.domainClass, params.id)

        render(template: "/tabs/"+params.template, plugin: params.plugin, model: model)
    }
}
