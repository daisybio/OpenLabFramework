package org.openlab.genetracker

import org.openlab.main.*;
import org.openlab.genetracker.vector.Vector;

class Recombinant extends DataObject{

	static mapping = {
		table 'gtRecombinant'
		cache true
		projects cache:true
		genes cache:true
	}
		
	static String type = "recombinant"
	static String typeLabel ="Recombinant"
	
	static hasMany = [projects: Project, genes: Gene]
	
	Vector vector
	
	String notes
	
	String toString(){
		if(genes && vector)
		{
			"" + genes.each{it.toString() + " - "} + vector
		}
		else
		{
			"no genes assigned - " + vector
		}
	}
	
	//make dataobjects target for searchable plugin
	static searchable = {
		mapping {
			vector component: true
			genes component: true
			projects reference: true
		}
	}
	
}
