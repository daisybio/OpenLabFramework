package org.openlab.main

/**
 * Encapsulate projects in hierarchy to distinguish between different labs.
 * @author markus.list
 *
 */
class Laboratory extends MainObject{

	String name
	String description
	
	static hasMany = [projects: Project]
	
	static constraints = {
		name(unique: true)
		description()
	}
	
	static mapping = {
		tablePerHierarchy false
		table 'olfLaboratory'
	}
	
	String toString()
	{
		name
	}
}
