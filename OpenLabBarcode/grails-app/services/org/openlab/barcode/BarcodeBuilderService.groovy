package org.openlab.barcode

import org.openlab.main.*;

class BarcodeBuilderService {

	def settingsService
	
	def getParamsFromBarcode(Barcode barcode)
	{
		def printerParams = []
		
		printerParams << "barcodeValue="+ buildBarcodeId(barcode).encodeAsURL() << "barcodeText=${barcode.description.encodeAsURL()}" << "text=${barcode.text.encodeAsURL()}" << "description=${barcode.dataObject.toString().replace(' - ', '\n').encodeAsURL()}"
		
		if(barcode.label.parameters && barcode.label.parameters.length() > 0)
			printerParams += barcode.label.parameters.split('&')
		
		else printerParams
		
	}
	
	def buildBarcodeId(def barcode)
	{
		def barcodeSize = settingsService.getDefaultSetting(key: "barcode.barcodeLength")?:16
		
		def string = "" + barcode.site.siteLetter + barcode.barcodeDataObject.typeLetter + barcode.id
		
		if(string.length() > barcodeSize) log.warn("Maximal Barcode length exceeded. Barcode: ${string}")
		
		string = string.substring(0,4) + getZeros(barcodeSize - string.length()) + string.substring(4)
		
		return string
	}
	
	def getZeros(def n)
	{
		def s = ""
		for(int i = 0; i < n; i++)
		{
			s += "0"
		}
		
		return s
	}
	
	def buildBarcodeFromId(def scanId)
	{
		def siteLetter = scanId.substring(0,2)
		def typeLetter = scanId.substring(2,2)
		def barcodeId = removeLeadingZeros(scanId.substring(4))
		
		if(barcodeId)
			Barcode.findById(barcodeId)
		else null
	}
	
	def removeLeadingZeros(def input)
	{
		String regex = "^0*";	
		input.replaceAll(regex, "");
	}
}
