package org.openlab.genetracker

import org.openlab.genetracker.model.*;
import grails.converters.*;
import org.openlab.main.*;
import org.codehaus.groovy.grails.commons.ApplicationHolder

/**
 * Scaffolded controller for Gene that is also housing methods for
 * XML/JSON and NCBI interaction.
 * @author markus.list
 *
 */
class GeneController extends DataObjectController{
	
	def scaffold = Gene
	def NCBIParserService
    
	/**
	 * Show NCBI JSON output for a accession number
	 */
	def ncbiAsJSON = {
		if(params.accessionNr) render NCBIParserService.parseNcbiXmlToMap(params.accessionNr) as JSON
		else return null
	}
	
	/**
	 * Show NCBI XML output for a accession number
	 */
	def ncbiAsXML = {
		if(params.accessionNr) render NCBIParserService.parseNcbiXmlToMap(params.accessionNr) as XML
		else return null
	}
	
	/**
	 * List or show a Gene as XML
	 */
    def xmlList = {
        render Gene.list() as XML
    } 

    def xmlShow = {
        render Gene.get(params.id) as XML
    }
	
	/**
	 * Create a new gene using the NCBIParserService
	 */
	def createWithNcbi = {
		if(params.accessionNr && params.accessionNr != "accession number"){
			//get properties map from service
			def ncbiMap = NCBIParserService.parseNcbiXmlToMap(params.accessionNr)

			if(!ncbiMap.accessionNumber || ncbiMap.accessionNumber == "")
			{
				flash.message = "Could not process accession number. Either it is wrong or NCBI cannot be reached."
				return redirect(action: "create", params: params)
			}
			//try to find corresponding organism
			def organism = Organism.findByName(ncbiMap.organism)
			
			//try to find by short name if latin name did not work
			if(!organism) organism = Organism.findByDescription(ncbiMap.organism)
			
			//replace string with object organism or remove if not found
			if(organism){
				ncbiMap.remove("organism")
				ncbiMap.'organism.id' = organism.id
			}
			else ncbiMap.remove("organism")
			
			ncbiMap = ncbiMap + params
			
			ncbiMap.name = ncbiMap.name + "-W"
			ncbiMap.accessionNumber = ncbiMap.accessionNumber + "-W"
			
			ncbiMap.isNCBIsequence = true
        	//render create to let the user check the values
			redirect(action:"create", params: ncbiMap)
		}
		
		else{
			flash.message = "Could not create gene with NCBI: Accession number was missing!"
			redirect(action:"create", params: params)
		}
	}

	/**
	 * Copies all persistent properties from one gene to another
	 */
	def cloneGene = { oldGene ->
		
		def newGene = new Gene()
		
		def domainClass = ApplicationHolder.application.getDomainClass(oldGene.getClass().name)
		
		domainClass?.persistentProperties.each{prop ->
			if((!prop.association || prop.oneToOne) && (prop.name != "id"))
			{
				newGene."${prop.name}" = oldGene."${prop.name}"
			}
		}
		
		return newGene
	}
	
	/**
	 * Takes a variant name and its abbreviation as input to create a variant of a gene.
	 */
	def createVariant = {
		
		def oldGene = Gene.get(params.id)
		def otherVariantGenes = Gene.findAllByWildTypeGeneAndGeneType(oldGene, params.type)
		def newGene = cloneGene(oldGene)
		
		if(oldGene.accessionNumber) newGene.accessionNumber = oldGene.accessionNumber + "-" + params.typeShort +(otherVariantGenes.size()+1)

		//add original gene
		newGene.wildTypeGene = oldGene
		newGene.geneType = params.type

		//alter name
		newGene.name = oldGene.name + "-" + params.typeShort +(otherVariantGenes.size()+1)
		
		println newGene.save()
		
		if(newGene.hasErrors())
		{
			newGene.errors.each{
				println it
			}
		}
		
		if(!newGene.hasErrors() && newGene.save())
		{
			flash.message = "Created variant ${params.type} ${newGene} from Gene ${oldGene}."
			
			oldGene.projects.each{
				it.addToObject(newGene).save(flush:true)
			}
			
			redirect(action:"show", id: newGene.id, params: [bodyOnly: true])
		}
		else
		{
			flash.message = "Creation of ${params.type} variant failed!" + oldGene.errors.each{it.toString()}
			redirect(action:"show", id: oldGene.id, params: [bodyOnly: true])
		}
	}
	
	def updateVariantList = {
		
		def gene = Gene.get(params.id)
		def variants 
		
		if(params.filterParam != "")
			variants = Gene.findAllByWildTypeGeneAndGeneType(gene, params.filterParam)
		else variants = Gene.findAllByWildTypeGene(gene)
		
		render(template:"/layouts/variantList", plugin: "gene-tracker", model: [geneVariants: variants])
	}
}
