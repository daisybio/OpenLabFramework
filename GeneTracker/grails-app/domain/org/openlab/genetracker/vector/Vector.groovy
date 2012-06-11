package org.openlab.genetracker.vector

import org.openlab.main.*

public class Vector{
	
	static mapping = {
		table 'gtVector'
		discriminator column: [name:'vectorType', length:50] 
		cache true
	}
	
	String label
	String type
	String description
	
	static constraints = {
		label(unique:true)
		type(inList:["Acceptor", "Integration (First)", "Integration (Second)", "Cloning Vector"])
	}
	
	//make dataobjects target for searchable plugin
	static searchable = {
		mapping {
			//needed for suggestions in searchable
			spellCheck "include"
		}
	}
	
	String toString(){
		label
	}
}
