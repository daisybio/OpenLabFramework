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
package org.openlab.content

import org.hibernate.criterion.CriteriaSpecification
import org.openlab.main.*;
import org.openlab.security.*;

class DashboardController {

	def springSecurityService
	
	def index = {

        withMobileDevice {
            redirect(action: "mobile_index")
            return
        }

		[:]
	}
	
	def dashboard = {
        if(!springSecurityService.isLoggedIn()){
            redirect(action: "auth", controller: "login", params: [bodyOnly:false])
            return
        }

        withMobileDevice {
            redirect(action: "mobile")
            return
        }

		def lastModifiedMax = params.lastModifiedMax?:10
        //get user
        def username = springSecurityService?.getPrincipal().username

		//get last modified by anyone
		def lastModifiedByAny = DataObject.withCriteria {
            createAlias("shared", "sh", CriteriaSpecification.LEFT_JOIN)
            createAlias("creator", "cr", CriteriaSpecification.LEFT_JOIN)
            or{
                eq("cr.username", username)
                isNull("accessLevel")
                eq("accessLevel", "open")
                eq 'sh.username', username

            }
            maxResults lastModifiedMax
            order "lastUpdate", "desc"
        }


		//get last modified by user
		def lastModifiedByUser = DataObject.withCriteria{
            lastModifier{
                eq("username", username)
            }
            maxResults lastModifiedMax
            order "lastUpdate", "desc"
        }
		
		//return model
		[lastModifiedByUser: lastModifiedByUser, lastModifiedByAny: lastModifiedByAny, lastModifiedMax: lastModifiedMax]
	}

    def mobile = {
        [:]
    }

    def mobile_index = {
        [:]
    }
	
}
