package org.openlab.genetracker;

import org.openlab.main.DataObject;
import org.openlab.genetracker.*;
import org.openlab.main.Project;

public class Gene extends DataObject implements Serializable{
	
	static mapping = {
		table 'gtGene'
		sequence type: 'text'
		cache true
	}

	static constraints = {
		name([blank: false, unique:true])
		description()
		accessionNumber()
		notes()
		geneType(inList: ["Wildtype", "Knockdown", "Promotor", "Mutant", "cDNA derived", "Fragment"])
		wildTypeGene(nullable:true)
		origin(nullable: true)
		originDescription(nullable:true)
		alternativeSplicing()
		mutation()
		organism()
		length()
		orfStart()
		orfStop()
		primers(nullable: true)
	}
	
	String name
	String sequence
	String accessionNumber
	int length
	Origin origin
	String originDescription
	String description
	int orfStart
	int orfStop
	Organism organism
	Gene wildTypeGene
	String notes
	String geneType
	boolean alternativeSplicing
	boolean mutation
	
	static String type = "gene"
	static String typeLabel = "Gene"
	
	//make dataobjects target for searchable plugin
	static searchable = {
			primers component: true
			wildTypeGene reference: true
			projects reference:true
	}
	
	static belongsTo = [Project]
		
	static hasMany = [projects: Project, primers: Primer]
		
    String toString()
	{
		name
	}
}
