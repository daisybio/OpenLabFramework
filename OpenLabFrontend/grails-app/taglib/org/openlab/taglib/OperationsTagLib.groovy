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
package org.openlab.taglib;

import org.apache.jasper.compiler.Node.ParamsAction;

public class OperationsTagLib {

	def moduleHandlerService
	
	def includeOperationsForType = {attrs ->
		
		def interestedModules = moduleHandlerService.getInterestedModules(domainClass: attrs.domainClass, type: "operations")
		
		interestedModules.each{ module ->
			out << render(template: '/operations/'+module.getTemplateForDomainClass(attrs.domainClass), plugin: module.getPluginName(),
					model: module.getModelForDomainClass(attrs.domainClass, params.id))
		}
	}
	
	def includeBookmarkThisPageLink = {attrs ->
		def url = request.getRequestURL().toString().replaceFirst(".dispatch", "").replaceFirst("/grails", "") + '/' + attrs.id
		def title = attrs.domainClass.get(attrs.id)
		
		out << """<li><a href="" id="bookmarkLink" onClick="if(navigator.appName=='Microsoft Internet Explorer')window.external.AddFavorite('${url}', '${title}');
		else if(navigator.appName=='Netscape')window.sidebar.addPanel('${title}','${url}','');else alert('Bookmarks not supported on your browser!');return false;">
		<img src="${resource(dir:'images/skin',file:'olf_bookmark_small.png')}"/> Bookmark this page</a></li>"""
	}
}
