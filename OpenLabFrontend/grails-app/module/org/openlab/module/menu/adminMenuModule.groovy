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