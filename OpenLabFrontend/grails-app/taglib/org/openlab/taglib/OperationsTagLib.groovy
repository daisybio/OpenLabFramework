package org.openlab.taglib;

import org.apache.jasper.compiler.Node.ParamsAction;

public class OperationsTagLib {

	def moduleHandlerService
	
	def includeOperationsForType = {attrs ->
		
		println attrs.domainClass
		
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
