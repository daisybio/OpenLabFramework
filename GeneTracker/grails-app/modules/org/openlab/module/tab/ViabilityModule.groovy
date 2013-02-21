package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.genetracker.*;

class ViabilityModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass) 
	{
		if(domainClass == "gene") return "viabilityTab"
		
		else return null
	}

    @Override
    def getMobileTemplateForDomainClass(Object domainClass) {
        return null
    }

    def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && domainClass == "gene") return true
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass == "gene")
		{
			def userNames = org.openlab.security.User.list().collect{it.username}
			def gene = Gene.get(id)
			def viabilities = Viability.findAllByGene(gene)
            def recombinantService = new org.openlab.genetracker.RecombinantsService()
            def cellLineData = recombinantService.recombinantsWithGene(id)
			return [viabilities: viabilities, gene:gene, userNames: userNames, cellLineData: cellLineData]
		}
	}

    @Override
    def isMobile() {
        return false
    }
}
