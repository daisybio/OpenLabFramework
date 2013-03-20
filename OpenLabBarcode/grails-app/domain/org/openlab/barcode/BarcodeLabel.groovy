package org.openlab.barcode

class BarcodeLabel {

	String name
	String barcodeType
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
