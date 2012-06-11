package org.openlab.storage

import org.openlab.main.*

class StorageElement {

	String description
	int xcoord
	int ycoord
	
	DataObject dataObj
	Box box
	
	static belongsTo = Box
	
	def lastUpdate
	
	//check if cell free or identity
    static constraints = {
		box(validator: { val, obj ->
			def element = val.elements.find{e -> e.xcoord == obj.xcoord && e.ycoord == obj.ycoord}
			if(!element) return true
			else if(element == obj) return true
			else{
				return false
			}
		})
		
		//description(unique:'box')
    }
	
	String toString()
	{
		dataObj.toString()
	}
}
