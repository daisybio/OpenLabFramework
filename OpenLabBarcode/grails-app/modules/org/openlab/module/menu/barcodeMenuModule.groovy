package org.openlab.module.menu

import org.openlab.module.*;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import groovy.xml.MarkupBuilder

class barcodeMenuModule implements MenuModule{

	def springSecurityService
	
	def getPriority()
	{
		return -19
	}
	
	def getMenu()
	{
		def isAdmin = SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')
		
		if(isAdmin)
		{
			def writer = new StringWriter()
			def xml = new MarkupBuilder(writer)
			def action = "list"
		
			xml.root
			{
				submenu(label: 'Barcode')
				{
					menuitem(controller: 'barcodeSite', action: action, label: 'Sites')
					menuitem(controller: 'barcodeLabel', action: action, label: 'Labels')
					menuitem(controller: 'barcodeDataObject', action: action, label: 'Supported Types')
					menuitem(controller: 'barcode', action: action, label: 'Barcodes')
					menuitem(controller: 'barcode', action: "printerSettings", label: 'Printer Settings')
				}
			}
		
			return writer.toString()
		}
		
		else return null
	}
	
}
