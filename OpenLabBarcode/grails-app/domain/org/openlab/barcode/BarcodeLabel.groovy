package org.openlab.barcode

class BarcodeLabel {

	String name
	String barcodeType
	String parameters
	String xml
	
	static mapping = {
		table 'olfBarcodeLabel'
		xml type: 'text'
	}
	
	String toString()
	{
		name
	}
}
