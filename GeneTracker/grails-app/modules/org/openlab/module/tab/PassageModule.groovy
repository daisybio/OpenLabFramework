package org.openlab.module.tab;

import org.openlab.module.*;

public class PassageModule implements Module {

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass) 
	{
		if(domainClass.startsWith("cellLineData")) return "passageTab"
		
		else return null
	}
	
	def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && domainClass.startsWith("cellLineData")) return true
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass.startsWith("cellLineData"))
		{
			def userNames = org.openlab.security.User.list().collect{it.username}
			
			return [userNames: userNames]
		}
	}
}
