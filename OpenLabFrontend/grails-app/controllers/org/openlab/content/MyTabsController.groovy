package org.openlab.content

import org.openlab.module.Module

class MyTabsController {

    def grailsApplication

    def renderTab(){

        Module module = grailsApplication.getArtefact("Module", params.module).referenceInstance

        def model = module.getModelForDomainClass(params.domainClass, params.id)

        render(template: "/tabs/"+params.template, plugin: params.plugin, model: model)
    }

    def renderMobileTab(){

        Module module = grailsApplication.getArtefact("Module", params.module).referenceInstance

        def templateModel = module.getModelForDomainClass(params.domainClass, params.id)
        def model = ['templateName': "/tabs/" + params.template, templatePlugin: params.plugin,
        lastController: params.domainClass, lastId: params.id, templateModel: templateModel]

        render(view: "/tabs/mobile_tab", model: model)
    }
}
