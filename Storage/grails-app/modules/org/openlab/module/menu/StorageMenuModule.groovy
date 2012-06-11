package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class StorageMenuModule implements MenuModule{

	def getPriority()
	{
		-5
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		xml.root
		{
			submenu(label: 'Storage')
			{
				menuitem(controller: 'storage', action: 'list', label: 'Browse Storage')
				menuitem(controller: 'storage', action: 'edit', label: 'Manage Boxes')
				submenu(label: 'Configuration')
				{
					menuitem(controller: 'storageType', action: 'list', label: 'Edit StorageTypes')
					menuitem(controller: 'compartment', action: 'list', label: 'Edit Compartments')
					menuitem(controller: 'storageLocation', action: 'list', label: 'Edit Storage Locations')
				}
			}
		}
		
		return writer.toString()
	}
	
}
