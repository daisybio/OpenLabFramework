package org.openlab.module.operations

import org.openlab.module.*;
import org.openlab.genetracker.*;

class GeneOperationsModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass)
	{
		if(domainClass == "gene") return "geneOperations"
	}

    @Override
    def getMobileTemplateForDomainClass(Object domainClass) {
        return null
    }

    def isInterestedIn(def domainClass, def type)
	{
		if((type == "operations") && (domainClass == "gene")) return true
		return false
	}
		
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass == "gene")
		{
			def gene = Gene.get(id)
			[geneInstance: gene]
		}
	}

    @Override
    def isMobile() {
        return false
    }
}
