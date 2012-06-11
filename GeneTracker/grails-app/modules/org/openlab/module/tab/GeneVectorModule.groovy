package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.genetracker.vector.*;
import org.openlab.genetracker.*;

class GeneVectorModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass)
	{
		if((domainClass == "gene")) return "geneVectorTab"
		
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
			def gvectors = Recombinant.list().findAll{it.genes?.contains(gene)}
			def vectors = Vector.list().findAll{it.type != "Acceptor"}
			
			[gene: gene, geneVectors: gvectors, vectors: vectors.collect{it.toString()}]
		}
	}
}
