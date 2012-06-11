package org.openlab.main;

import org.openlab.security.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * MainObject with History related fields and methods
 * @author markus.list
 *
 */
public class MainObject{
	
	def springSecurityService
	
	Date dateCreated
	Date lastUpdate
	
	User creator
	User lastModifier 
	
	static mapping = {
		tablePerHierarchy false
		table 'olfMainObject'
	}
	
	static constraints = {
		creator(nullable:true)
		lastModifier(nullable:true)
		lastUpdate(nullable:true)
		dateCreated()
	}
	
	static String dbName = ConfigurationHolder.config.openlab.database.name
	
	//make objects searchable
	static searchable = {
		mapping {
			//needed for suggestions in searchable
			spellCheck "include"
		}
	}
	
	/**
	 * update history fields on insert and update
	 */
	def beforeInsert = {
		def principal = springSecurityService.getPrincipal()
		
		if(principal)
		{
			def user = User.findByUsername(principal.username)	
			creator = user
			
			lastUpdate = new Date()
			lastModifier = user
		}
	}
	
	def beforeUpdate = {

		def principal = springSecurityService.getPrincipal()
		
		if(principal)
		{
			lastModifier = User.findByUsername(principal.username)
			lastUpdate = new Date()
		}
	}
}
