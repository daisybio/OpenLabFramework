package org.openlab.module.addin

import org.openlab.module.*;

class BarcodeCreationAddinModule implements AddinModule{

	def getName()
	{
		"barcode"
	}
	
	def getTemplate()
	{
		"barcodeCreation"
	}
	
	def getPluginName()
	{
		"open-lab-barcode"
	}
}
