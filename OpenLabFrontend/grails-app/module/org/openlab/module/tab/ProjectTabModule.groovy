package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.main.*;

class ProjectTabModule implements Module{
	
	def getPluginName() {
		""
	}

	def getTemplateForDomainClass(def domainClass)
	{
		if(domainClass == "project") return "projectTab"
		
		else return null
	}
	
	def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && domainClass == "project") return true
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass == "project")
		{
			[dataObjects: DataObject.withCriteria{
                    projects{
                        eq("id", Long.valueOf(id))
                    }
                }.sort{it.toString()}
            ]
		}
	}

    def isMobile(){
        return true;

    }

    def getMobileTemplateForDomainClass(def domainClass)
    {
        if(domainClass == "project") return "projectTab"

        else return null
    }
}
