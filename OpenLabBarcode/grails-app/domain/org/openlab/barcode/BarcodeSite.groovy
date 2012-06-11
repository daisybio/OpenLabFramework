package org.openlab.barcode

class BarcodeSite {

	String name
	String RepositoryName
	String description
	String SiteLetter
	
	static constraints = {
		SiteLetter(maxSize: 2)
	}
	
	static mapping = {
		table 'olfBarcodeSite'
	}
	
	String toString()
	{
		name
	}
	
}
