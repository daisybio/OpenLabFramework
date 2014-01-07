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
