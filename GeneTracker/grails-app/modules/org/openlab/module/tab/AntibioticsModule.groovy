package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.genetracker.*;

class AntibioticsModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass) 
	{
		if(domainClass.startsWith("cellLineData")) return "antibioticsTab"
		
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
			def cellLineData = CellLineData.get(id)
			def unusedAntibiotics = Antibiotics.list()
			unusedAntibiotics.removeAll(cellLineData.antibiotics.collect{it.antibiotics}.toList())
			
			return [unusedAntibiotics: unusedAntibiotics.collect{it.toString()}]
		}
	}
}