package org.openlab.module.tab;

import org.openlab.module.*
import org.openlab.genetracker.Passage
import org.openlab.genetracker.CellLineData;

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
			def cellLineData = CellLineData.get(id)

			return [cellLineData: cellLineData, passages: Passage.findAllByCellLineData(cellLineData)]
		}
	}
}
