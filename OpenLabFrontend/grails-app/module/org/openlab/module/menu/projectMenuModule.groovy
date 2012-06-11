package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


class projectMenuModule implements MenuModule{

	def springSecurityService
	def isAdmin = SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')
	
	def getPriority()
	{
		0
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		xml.root
		{
			submenu(label: 'Projects')
			{
				menuitem(controller: 'project', action: 'create', label: 'Create Project')
				menuitem(controller: 'project', action: 'list', label: 'List Projects')
			}
		}
		
		return writer.toString()
	}
}
