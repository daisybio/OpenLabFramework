package org.openlab.content

import org.openlab.main.*;
import org.openlab.security.*;

class DashboardController {

	def springSecurityService
	
	def index = {
		redirect action: "dashboard"
	}
	
	def dashboard = {
		def lastModifiedMax = params.lastModifiedMax?:10
		
		//get last modified by anyone
		def lastModifiedByAny = DataObject.list(max: lastModifiedMax, sort: "lastUpdate", order: "desc")
		
		//get user
		def username = springSecurityService.getPrincipal().username
		
		//get last modified by user
		def lastModifiedByUser = DataObject.findAllByLastModifier(User.findByUsername(username)).sort{it.lastUpdate}
		
		//shorten list if necessary
		if(lastModifiedByUser.size() > lastModifiedMax) 
			lastModifiedByUser = lastModifiedByUser[0..(lastModifiedMax-1)]
		
		//return model
		[lastModifiedByUser: lastModifiedByUser, lastModifiedByAny: lastModifiedByAny, lastModifiedMax: lastModifiedMax]
	}
	
}
