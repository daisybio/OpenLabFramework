package org.openlab.taglib;

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

public class BuildMenuTagLib {

	def moduleHandlerService
	
	def buildMenu = {
			def menuModules = moduleHandlerService.getMenuModules().sort {it.getPriority()}.reverse()
			
			out << gui.menubar(id: 'topMenuBar', renderTo:'top1'){
					
					menuModules.each{ module ->
						
						def xml = module.getMenu()
						
						if(xml != null)
						{
							def xmlMap = new groovy.util.XmlSlurper().parseText(xml)
							
							gui.submenu(label: (xmlMap.submenu.@label as String))
							{
								xmlMap.submenu.menuitem.each{ item ->
									createMenuItem(item)
								}
								if((xmlMap.submenu.submenu.@label as String) != "")
								{
									gui.submenu(label: (xmlMap.submenu.submenu.@label as String))
									{
										xmlMap.submenu.submenu.menuitem.each{ item ->
											createMenuItem(item)
										}
									}
								}
							}
						}
					}
			}
	}
	
	def createMenuItem(def item)
	{
		def link = createLink(controller: (item.@controller as String), action: (item.@action as String))
		def remoteF = remoteFunction(controller: (item.@controller as String), params: [bodyOnly:true], method: "get", action: (item.@action as String), update: [success: 'body', failure: 'body']) + 'return false;'
		gui.menuitem(url: link, helpText:'', onclick: remoteF)
		{
			item.@label as String
		}
	}
}