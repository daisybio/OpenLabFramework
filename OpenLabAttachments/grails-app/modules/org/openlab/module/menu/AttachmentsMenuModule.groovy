package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class AttachmentsMenuModule implements MenuModule{

	def getPriority()
	{
		5
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def controller = "dataObjectAttachment"
		
		xml.root
		{
			submenu(label: 'Attachments')
			{
				menuitem(controller: controller, action: 'list', label: 'List All Attachments')
			}
		}
		
		return writer.toString()
	}
}
