package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class geneMenuModule implements MenuModule{

	def getPriority()
	{
		10
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def controller = "gene"
		
		xml.root
		{
			submenu(label: 'Gene')
			{
				menuitem(controller: controller, action: 'create', label: 'Create Gene')
				menuitem(controller: controller, action: 'list', label: 'List Genes')
				menuitem(controller: "recombinant", action: 'list', label: 'List Vector Combinations')
			}
		}
		
		return writer.toString()
	}
}

