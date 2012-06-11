package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class CellLineMenuModule implements MenuModule{
	
	def getPriority()
	{
		8
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def controller = "cellLineData"
		
		xml.root
		{
			submenu(label: 'Cell Line Data')
			{
				menuitem(controller: controller, action: 'create', label: 'Create Cell Line Data')
				menuitem(controller: controller, action: 'list', label: 'List Cell Line Data')
			}
		}
		
		return writer.toString()
	}
}
