/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
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
