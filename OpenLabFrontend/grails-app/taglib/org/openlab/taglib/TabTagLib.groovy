package org.openlab.taglib

/**
 * TagLib that looks for modules interested to contribute a tab to a domainObject representation
 * @author markus.list
 */
class TabTagLib {

	def moduleHandlerService
	
	/*
	 * return [] of interested modules
	 */
	def getInterestedModules = { attrs ->
		moduleHandlerService.getInterestedModules(domainClass: attrs.domainClass, type: "tab").collect{it.getPluginName()}
	}
	
	/*
	 * return a number of <gui:tab> elements that are filled with content from module specific templates
	 */
	def renderInterestedModules = { attrs ->
		
		def interestedModules = moduleHandlerService.getInterestedModules(domainClass: attrs.domainClass, type: "tab")
		
		interestedModules.each{ module ->
			try{
				out << render(template: '/tabs/'+module.getTemplateForDomainClass(attrs.domainClass), plugin: module.getPluginName(), 
					model: module.getModelForDomainClass(attrs.domainClass, params.id))
			}catch(Exception e)
			{
				log.error("Error rendering ${module} " + e.getMessage() + e.getStackTrace())
			}		
		}
	}
}
