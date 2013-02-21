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
	def getInterestedMobileModules = { attrs ->
		def interestedModules = moduleHandlerService.getInterestedMobileModules(domainClass: attrs.domainClass, type: "tab")

        interestedModules.collect{
            def template = it.getMobileTemplateForDomainClass(attrs.domainClass)
            def name = template.toString().replace("mobile_", "").replaceAll(/\B[A-Z]/){ " $it" }.capitalize()-"Tab"
            def plugin = it.getPluginName()

            def targetLink = g.createLink(controller: "myTabs", action: "renderMobileTab",
                    params: [template: template, plugin: plugin, domainClass: attrs.domainClass, id: attrs.id, module: it.getClass().getName()])
            out << """<li data-theme="c" data-corners="false" data-shadow="false" data-iconshadow="true" data-wrapperels="div" data-icon="arrow-r" data-iconpos="right" class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c"><div class="ui-btn-inner ui-li"><div class="ui-btn-text">
            <a href="${targetLink}" data-transition="slide" class="ui-link-inherit">
                    ${name}
            </a>
                </div><span class="ui-icon ui-icon-arrow-r ui-icon-shadow">&nbsp;</span></div></li>"""
        }
	}
	
	/*
	 * return a number of <gui:tab> elements that are filled with content from module specific templates
	 */
	def renderInterestedModules = { attrs ->

		def interestedModules = moduleHandlerService.getInterestedModules(domainClass: attrs.domainClass, type: "tab")

        out << "<script type='text/javascript'>"
        out << "var tabView = new YAHOO.widget.TabView();"

		interestedModules.each{ module ->

            try{
                def template = module.getTemplateForDomainClass(attrs.domainClass)
                def plugin = module.getPluginName()

                out << "YAHOO.plugin.Dispatcher.delegate(new YAHOO.widget.Tab({"
				out << "label: '${template.toString().replaceAll(/\B[A-Z]/){ " $it" }.capitalize()-"Tab"}',"
                out << "dataSrc: '" + g.createLink(controller: "myTabs", action: "renderTab",
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
