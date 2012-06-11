package org.openlab.genetracker.vector

class CloningVector extends Vector{

	static mapping = { 	
		discriminator value: "cloning"
		cache true
	}
	
	String toString(){
		label
	}
}
