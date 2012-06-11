package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.genetracker.vector.*;
import org.openlab.genetracker.*;

class GeneVariantModule implements Module{
	
	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass)
	{
		if((domainClass == "gene")) return "geneVariantTab"
		
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
			def gene = Gene.get(id)
			def variants = Gene.findAllByWildTypeGene(gene)
			def types = variants.collect{it.geneType}.unique()
						
			[gene: gene, geneVariants: variants, types: types]
		}
	}
}
