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

import org.openlab.genetracker.vector.*;

class VectorController {

	def scaffold = Vector

    def save = {  	
		
		def className = "Vector"
		
		switch(params.type)
		{
			case "Acceptor":
				className = Acceptor.class.getName()
				break;
			
			case "Integration (First)":
				className = IntegrationFirst.class.getName()
				break;
			
			case "Integration (Second)":
				className = IntegrationSecond.class.getName()
				break;
			
			case "Cloning Vector":
				className= CloningVector.class.getName()
				break;
		}
		
		def vector = grailsApplication.getDomainClass(className).newInstance()
		vector.properties = params
		
        if (vector.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'vector.label', default: 'Vector'), vector.id])}"
            redirect(action: "show", id: vector.id, params: params)
        }
        else {
        	params.bodyOnly = true
			render(view: "create", model: [vector:vector])
        }
    }
}
