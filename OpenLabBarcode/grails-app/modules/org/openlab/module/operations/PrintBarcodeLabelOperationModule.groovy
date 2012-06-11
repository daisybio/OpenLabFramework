package org.openlab.module.operations

import org.openlab.module.*;
import org.openlab.barcode.*;
import org.openlab.main.*;

class PrintBarcodeLabelOperationModule implements Module{

	def getPluginName() {
		"open-lab-barcode"
	}

	def getTemplateForDomainClass(def domainClass)
	{
		return "printBarcodeLabelOperations"
	}
	
	def isInterestedIn(def domainClass, def type)
	{
		if((type == "operations") && (BarcodeDataObject.list().find{it.shortName == domainClass})) return true
		return false
	}
		
	def getModelForDomainClass(def domainClass, def id)
	{
		def barcode = Barcode.findById(id)
		def dataObj = DataObject.findById(id)
		
		if(barcode)
		{
			[barcode: barcode, dataObj: dataObj]
		}
		else [:]
	}
	
}
