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
