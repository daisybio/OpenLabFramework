package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class settingsMenuModule implements MenuModule {

	def getPriority()
	{
		-29
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		xml.root
		{
			submenu(label: 'Settings')
			{
				menuitem(controller: 'usersettings', action: 'user', label: 'General')
				menuitem(controller: 'usersettings', action: 'addins', label: 'Addins')
			}
		}
		
		return writer.toString()
	}
	
}
