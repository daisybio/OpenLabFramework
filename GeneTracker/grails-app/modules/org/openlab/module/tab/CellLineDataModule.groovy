package org.openlab.module.tab

import org.openlab.module.*;

class CellLineDataModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass) 
	{
		if(domainClass == "gene") return "cellLineDataTab"
		
		else return null
	}
	
	def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && (domainClass == "gene")) return true
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass == "gene")
		{
			return []
		}
	}
}
