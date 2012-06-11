package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class MasterDataMenuModule implements MenuModule{
	
	def getPriority()
	{
		-10
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		xml.root
		{
			submenu(label: 'Master Data')
			{
				menuitem(controller: 'organism', action: 'list', label: 'Organisms')
				menuitem(controller: 'mediumAdditive', action: 'list', label: 'Medium Additives')
				menuitem(controller: 'origin', action: 'list', label: 'Origins')
				menuitem(controller: 'cellLine', action: 'list', label: 'Cell Lines')
				menuitem(controller: 'cultureMedia', action: 'list', label: 'Culture Media')
				menuitem(controller: 'antibiotics', action: 'list', label: 'Anitbiotics')
				menuitem(controller: 'vector', action: 'list', label: 'Vectors')
			}
		}
		
		return writer.toString()
	}
}
