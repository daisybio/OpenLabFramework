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

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.grails.grailsui.MyMenuTagLib

public class BuildMenuTagLib {

	def moduleHandlerService
    static namespace = 'mygui'
	
	def buildMenu = {
			def menuModules = moduleHandlerService.getMenuModules().sort {it.getPriority()}.reverse()
			
			out << mygui.menubar(id: 'topMenuBar', renderTo:'top1'){
					
					menuModules.each{ module ->
						
						def xml = module.getMenu()
						
						if(xml != null)
						{
							def xmlMap = new groovy.util.XmlSlurper().parseText(xml)
							
							mygui.submenu(label: (xmlMap.submenu.@label as String))
							{
								xmlMap.submenu.menuitem.each{ item ->
									createMenuItem(item)
								}
								if((xmlMap.submenu.submenu.@label as String) != "")
								{
									mygui.submenu(label: (xmlMap.submenu.submenu.@label as String))
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
		mygui.menuitem(url: link, helpText:'', onclick: remoteF)
		{
			item.@label as String
		}
	}
}