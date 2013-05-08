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
package org.openlab.storage

/**
 * A box is 
 * the smallest compartment in the storage concept.
 * It contains StorageElements that are linked to 
 * coordinates of the box as well as DataObjects.
 * @author markus.list
 *
 */
class Box {

	//capacity can easily be calculated and does not need to be persisted
	static transients = ["capacity"]
	
	def capacity = {
		(xdim * ydim)
    }
    int xdim
    int ydim
    
    String description
    Compartment compartment
    static hasMany = [elements : StorageElement]
    static belongsTo = Compartment
	
    //A storage element should not exist without a box, therefore cascade on delete.
    static mapping = {
    	elements cascade:"all-delete-orphan"
    }
    
	Date lastUpdate
	
	static constraints = {
    	xdim(max:100)
    	ydim(max:100)
		lastUpdate(nullable:true)
		description(unique:'compartment')
    }
    
    String toString()
    {
    	"${compartment} - ${description}"
    }
	
	def beforeInsert()
	{
		lastUpdate = new Date()
	}
    def beforeUpdate(){
        lastUpdate = new Date()
    }
}


