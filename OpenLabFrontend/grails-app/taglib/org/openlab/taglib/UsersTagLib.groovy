package org.openlab.taglib

import org.openlab.security.*;

class UsersTagLib {
	
	def springSecurityService
	
	def listUsers = {
			User.list()
	}
	
	def currentUser = {
        def principal = springSecurityService.getPrincipal()
        def userName = principal.username
        
        User.findByUsername(userName)
	}
}
