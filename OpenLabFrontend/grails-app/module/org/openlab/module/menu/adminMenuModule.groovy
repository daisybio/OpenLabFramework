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
package org.openlab.module.menu

import org.openlab.module.*;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import groovy.xml.MarkupBuilder

class adminMenuModule implements MenuModule{

	def springSecurityService
	
	def getPriority()
	{
		return -20
	}
	
	def getMenu()
	{
		def isAdmin = SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')
		
		if(isAdmin)
		{
			def writer = new StringWriter()
			def xml = new MarkupBuilder(writer)
			def controller = "settings"
			def action = "list"
		
			xml.root
			{
				submenu(label: 'Administration')
				{
					menuitem(controller: 'settings', action: action, label: 'Change Settings')
					menuitem(controller: 'laboratory', action: 'list', label: 'Manage Laboratories')
					menuitem(controller: 'user', action: action, label: 'Manage Users')
					menuitem(controller: 'dataSource', action: 'show', label: 'Show Database Configuration')
				}
			}
		
			return writer.toString()
		}
		
		else return null
	}
}