/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.openlab.genetracker

import grails.converters.*;
import org.openlab.main.*;

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
		
		def domainClass = grailsApplication.getDomainClass(oldGene.getClass().name)
		
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


    def stats = {
       [chartType: params.chartType?:"piechart"]
    }
}
