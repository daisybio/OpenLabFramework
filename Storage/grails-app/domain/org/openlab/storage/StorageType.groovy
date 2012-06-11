package org.openlab.storage

import org.openlab.main.*;

class StorageType 
{
	StorageLocation location
	String description
	
    static hasMany = [compartments : Compartment]
    
	static constraints = {
		description(unique:true)
	}
	                  
    String toString()
	{
		description
	}
}
