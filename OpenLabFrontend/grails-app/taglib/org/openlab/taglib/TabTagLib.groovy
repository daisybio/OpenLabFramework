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

        println attrs
		def interestedModules = moduleHandlerService.getInterestedModules(domainClass: attrs.domainClass, type: "tab")

        out << "<script type='text/javascript'>"
        out << "var tabView = new YAHOO.widget.TabView();"

		interestedModules.each{ module ->

            try{
                def template = module.getTemplateForDomainClass(attrs.domainClass)
                def plugin = module.getPluginName()

                out << "YAHOO.plugin.Dispatcher.delegate(new YAHOO.widget.Tab({"
				out << "label: '${template.toString().replaceAll(/\B[A-Z]/){ " $it" }.capitalize()-"Tab"}',"
                out << "dataSrc: '" + g.createLink(controller: "tabs", action: "renderTab",
                        params: [template: template, plugin: plugin, domainClass: attrs.domainClass, id: attrs.id, module: module.getClass().getName()]) + "',"
                out << "cacheData: true"
                out << "}), tabView);"
			}catch(Exception e)
			{
				log.error("Error rendering ${module} " + e.getMessage() + e.getStackTrace())
			}		
		}

        out << "tabView.appendTo('tabs');"
        out << "</script>"

	}
}
