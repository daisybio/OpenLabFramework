package org.openlab.taglib

import org.openlab.security.*;

class UsersTagLib {
	
	def springSecurityService
    def settingsService
	
	def listUsers = {
			User.list()
	}
	
	def currentUser = {
        def principal = springSecurityService.getPrincipal()
        def userName = principal.username
        
        User.findByUsername(userName)
	}

    def leftCollapsed = {
        if(settingsService.exists(key: "left.column.collapse")) {
            println settingsService.getUserSetting(key: "left.column.collapse")
            out << settingsService.getUserSetting(key: "left.column.collapse")
        }
        else{
            out << false
        };
    }

    def rightCollapsed = {
        settingsService.getUserSetting(key: "right.column.collapse")
    }

    def userSetting = { attrs, body ->
        out << body() << settingsService.getUserSetting(key: attrs.key)
    }
}
