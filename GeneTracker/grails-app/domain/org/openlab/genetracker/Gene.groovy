/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
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
			wildTypeGene reference: true
			projects reference:true
	}
	
	static belongsTo = [Project]
		
	static hasMany = [projects: Project]
		
    String toString()
	{
		name
	}
}
