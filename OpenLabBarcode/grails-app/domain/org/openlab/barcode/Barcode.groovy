package org.openlab.barcode

import org.openlab.main.*;

class Barcode {
	
	static mapping = {
		//id generator:'foreign', params:[property:DataObject]
		table 'olfBarcode'
	}
	
	static constraints = {
		dataObject(nullable: false, unique: 'subDataObject')
		subDataObject nullable: true
	}
	
	static belongsTo = [dataObject: DataObject]
	
	BarcodeSite site
	BarcodeDataObject barcodeDataObject
	SubDataObject subDataObject
	DataObject dataObject
	BarcodeLabel label
	String text
	String description
	def lastUpdate
	
	String toString()
	{
		dataObject.toString()+"Label"
	}
}
