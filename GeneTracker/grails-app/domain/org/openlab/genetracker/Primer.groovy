package org.openlab.genetracker

import org.openlab.main.*;

class Primer extends SubDataObject{

	static mapping = {
		table 'gtPrimer'
		sequence type: 'text'
		cache true
	}
	
	static belongsTo = [gene: Gene]
	                    
	String label
	String direction
	String sequence
	
	static constraints = {
		direction(inList:["for", "rev"] )
	}
	
	String toString()
	{
		label + " - " + direction + " - " + sequence.toString().toUpperCase() 
	}
	
}
