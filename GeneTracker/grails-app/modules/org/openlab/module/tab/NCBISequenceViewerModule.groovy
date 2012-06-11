package org.openlab.module.tab

import org.openlab.module.*;

class NCBISequenceViewerModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass)
	{
		//if(domainClass == "gene") return "ncbiSequenceViewer"
		return false;
	}
	
	def isInterestedIn(def domainClass, def type)
	{
		//if((type == "tab") && (domainClass == "gene")) return true
		return false
	}
		
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass == "gene")
		{
			def gene = org.openlab.genetracker.Gene.get(id)
		
			[geneId: id, accessionNumber: gene.accessionNumber, originalGene: (gene.geneType == "Wildtype")]
		}
	}
}