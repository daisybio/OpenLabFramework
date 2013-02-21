package org.openlab.content

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
		
		//get last modified by anyone
		def lastModifiedByAny = DataObject.list(max: lastModifiedMax, sort: "lastUpdate", order: "desc")
		
		//get user
		def username = springSecurityService?.getPrincipal().username
		
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
