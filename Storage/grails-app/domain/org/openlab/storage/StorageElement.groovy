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

import org.openlab.main.*

class StorageElement {

	String description
	int xcoord
	int ycoord
	
	DataObject dataObj
    SubDataObject subDataObj
	Box box
	
	static belongsTo = Box
	
	def lastUpdate
	
	//check if cell free or identity
    static constraints = {
        subDataObj nullable:  true
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

    def afterUpdate = {
        box.lastUpdate = new Date()
    }
	
	String toString()
	{
		if(!subDataObj) dataObj.toString()
        else dataObj.toString() + " - " + subDataObj.toString()
	}
}
