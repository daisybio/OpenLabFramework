package org.openlab.barcode

import org.openlab.main.DataObject;

class BarcodeDataObject {

	String typeLetter
	String shortName   
	String fullName
	String defaultText
	String defaultDescription
	
	static mapping = {
		table 'olfBarcodeDO'
	}
	
	static constraints = {
		typeLetter(maxSize: 2)
		fullName validator: { val, obj ->
			if(org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("grailsApplication").getDomainClass(val.toString()))
				return true
			else return false
		}
	}
	
	String toString()
	{
		shortName+"LabelSupport"
	}
}
