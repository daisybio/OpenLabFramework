package org.openlab.dataimport.creator

import org.openlab.security.*;
import org.openlab.main.*;
import org.openlab.genetracker.*;
import org.openlab.genetracker.vector.*;
import org.openlab.storage.*;
import org.joda.time.*;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.openlab.dataimport.processor.*;
import org.openlab.dataimport.*;
import org.openlab.dataimport.creator.*;
import groovy.transform.InheritConstructors

@InheritConstructors
class GeneCreator extends GeneTrackerImportClass{

	def ncbiParser = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("NCBIParserService")
	def numOfUnnamedGenes = 0
	
	/*
	* Migrates Genes.
	* @depends users
	*/
   def createGenes(boolean useNcbi){
   
	   log.info "creating Genes..."
	   def userDateProcessor = new UserDateProcessor(importer)
	   def sequences = importer.getSequencesMap()
	   
	   importer.getGenes().each{gene ->
		   
		   //use accession number to obtain ncbi values
		   def ncbiMap = null
		   if(gene.accessionNumber && (useNcbi != false))
		   {
				ncbiMap = ncbiParser.parseNcbiXmlToMap(gene.accessionNumber)
		   }
		   
		   //set creator, lastModifier and corresponding dates
		   gene = userDateProcessor.setDateAndUserParams(gene)
		   
		   gene.geneType = "Wildtype"
	   
		   //println "gene:" + gene
		   //println "ncbiMap:" + ncbiMap
	   
		   //remove superfluous parameters
		   if(gene.name && ncbiMap?.name) ncbiMap.remove("name")
		   
		   if(gene.name) true
		   else if(gene.accessionNumber) gene.name = gene.accessionNumber
		   else{
			   numOfUnnamedGenes++
			   
			   gene.name = "unnamed imported gene ${numOfUnnamedGenes}"
		   }
		   
		   if(ncbiMap?.accessionNumber) ncbiMap.remove("accessionNumber")
		   
		   if(ncbiMap) gene += ncbiMap
		   
		   //find corresponding project
		   def projectId = importer.getProjectGeneMap()."${gene.oldId}"
		   def projectName = importer.getProjects().find{it.oldId == projectId}?.name
		   def project
		   
		   //default project if no project found
		   if(!projectName)
		   {
				project = Project.findByName("import")
				if(!project){
				   project = new Project(laboratory: Laboratory.findByName("Heidelberg"), name:"import", description:"import project for unassigned genes.").save(flush:true)
				}
		   }
		   else 
		   {
			   project = Project.findByName(projectName)
		   }
					   
		   //get length
		   if(!(ncbiMap?.length) && gene.length)
		   {
			   gene.orfStart = 0
			   gene.orfStop = 0
			   try{
				   int intLength = Double.valueOf(gene.length).round()
				   gene.length = intLength
			   }catch(NumberFormatException e)
			   {
				   gene.length = 0
			   }
		   }
		   else if(ncbiMap?.length && gene.length)
			   gene.remove("length")
		   
		   //set missing parameters
		   if(!gene.description) gene.description = "imported gene without description"
		   if(!gene.notes) gene.notes = ""
		   
		   if(true)
		   {
			   def o = Organism.findByName("Homo sapiens")
			   gene.organism = o
		   }
		   
		   gene.sequence = sequences."${gene.oldId}"?:""
		   if(!gene.accessionNumber) gene.accessionNumber = ""
		   
		   gene.remove("storageLocation") //?
		   
		   //create gene and add to project
		   def newGene = new Gene(gene)
		   
		   project.addToObject(newGene).save(flush:true)
		   if(project.hasErrors())
		   {
			   project.errors.each{
				   println it
			   }
		   }
		   else
		   {
			   gene.newId = newGene.id
		   }
	   }
   }
}
