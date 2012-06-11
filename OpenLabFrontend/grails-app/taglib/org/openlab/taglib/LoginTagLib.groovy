package org.openlab.taglib

class LoginTagLib {
	def springSecurityService  
	
	def loginControl = {
	      if(springSecurityService.isLoggedIn()){
			  out << """
			  Hello ${sec.loggedInUserInfo(field:'username')} 
			  [${link(action:"index", controller:"logout"){"Logout"}}]"""
	      }
	      else{
	    	  out << """[${link(action:"auth", controller:"login"){"Login"}}]"""
	      }
    }
}
	 

