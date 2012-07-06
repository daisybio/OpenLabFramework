package org.openlab.barcode

class BarcodeSite {

	String name
	String repositoryName
	String description
	String siteLetter
	
	static constraints = {
		siteLetter(maxSize: 2)
	}
	
	static mapping = {
		table 'olfBarcodeSite'
	}
	
	String toString()
	{
		name
	}
	
}
