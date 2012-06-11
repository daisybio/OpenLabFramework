package org.openlab.genetracker

import org.openlab.data.*;

/**
 * DataTableController for _cellLineDataTab.gsp and 
 * scaffolded controller for CRUD actions on CellLineData 
 * @author markus.list
 *
 */
class CellLineDataController extends DataTableControllerTemplate{

	def scaffold = CellLineData
	def RecombinantsService
	
	/**
	 * Copies elements from a persistentSet into a new list
	 * @param oldList
	 * @return
	 */
	def persistentCollectionCopy(def oldList){
		def newList = []
		
        oldList.each{
			newList << it
		}
		
		return newList
	}
	
	/*
	 * creates antibiotics
	 */
	def createAntibioticsWithConcentration(def antibiotics){
		def antibioticsWithC = []
		
		antibiotics.each{
			def newAC = new AntibioticsWithConcentration(antibiotics: it, concentration: "")
			
			if(newAC.hasErrors())
			{
				newAC.errors.each{
					log.error it.toString()
				}
			}
				
			else
			{
				newAC
			}
			
			antibioticsWithC << newAC
		}
		
		return antibioticsWithC
	}
	
	/**
	 * override save method so that CellLine's default values for
	 * goodies, antibiotics etc. can be added
	 */
    def save = {  	
			
			def cellLine = CellLine.get(params.cellLine.id)
			/**
			 * add any existing cellLine properties to params if non given
			 */
			if(cellLine.mediumAdditives)
				params.mediumAdditives = persistentCollectionCopy(cellLine.mediumAdditives)
			
			def antibiotics
			
			if(cellLine.antibiotics)
			{
				antibiotics = createAntibioticsWithConcentration(cellLine.antibiotics)
			}
			
			if((params.cultureMedia?.id.toString() == "null") && cellLine.cultureMedia)
				params.cultureMedia = cellLine.cultureMedia
	    	
			/**
			 * usual save as in scaffolded controller
			 */
			def cellLineData = new CellLineData(params)
	        if (cellLineData.save(flush: true)) {
	            if(antibiotics)
				{
					antibiotics.each{
						cellLineData.addToAntibiotics(it).save()
					}
				}
				
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'CellLineData.label', default: 'CellLineData'), cellLineData.id])}"
	            redirect(action: "show", id: cellLineData.id, params: params)
	        }
	        else {
	        	render(view: "create", model: [cellLineDataInstance: cellLineData])
	        }
	    }
	
	def cellLineDataAsJSON = {
		    
			//def gene = Gene.get(params.id)
			
		    def list = []
			
			Set cellLineData = RecombinantsService.recombinantsWithGene(params.id)
		
			//old and slow with too many queries
		    //def cellLineData = CellLineData.list().findAll{((it.firstRecombinant?.genes?.contains(gene)) || (it.secondRecombinant?.genes?.contains(gene))) }                    
			
			cellLineData.each {
				def id = it.id
		    	list << [
				    id: remoteLink(params: [bodyOnly:true], action: 'show', id: id, controller:'cellLineData', update: [success: "body", failure: "body"]){id},
	                cellLine: it.cellLine.toString(),
				    acceptor: it.acceptor.toString(),
	                firstRecombinant: it.firstRecombinant.toString(),
	                secondRecombinant: it.secondRecombinant?it.secondRecombinant.toString(): "",
	                cultureMedia: it.cultureMedia? it.cultureMedia.toString() : "",
				    notes: it.notes? it.notes.toString() : "",
	                dataUrl: remoteFunction(action: 'show', id: it.id, controller:'cellLineData', update: [success: "body", failure: "body"]),
		    		modifyUrls:
					(remoteLink(action:'show', controller:'cellLineData', id: it.id, params: [bodyOnly:true], update:'body'){"<img src=${createLinkTo(dir:'images/skin',file:'olf_arrow_right.png')} alt='Show' />"}+
					remoteLink(action:'removeTableRow', controller:'cellLineData', id: it.id,
	                			before: "return confirm('Are you sure?');",
	                			onSuccess: "javascript:GRAILSUI.dtCellLineData.requery();"){"<img src=${createLinkTo(dir:'images/skin',file:'olf_delete.png')} alt='Delete' />"})
	            ]
			}   	
		    
		    render tableDataAsJSON(jsonList: list)
	}
	
    def tableDataChange = {
	        def cellLineData = CellLineData.findById(params.id)

        	cellLineData."$params.field" = params.newValue
	        
        	cellLineData.save()
	        render "success"
    }
	
	def addTableRow = {
		return false
	}
	
	def removeTableRow = {
		CellLineData.get(params.id).delete()
		render "success"			
	}
	
	def updateFirstVector = {
		if(params.firstGene)
		{
			def recombinants = Recombinant.withCriteria{
				createAlias("genes", "g")
				and{
					eq("g.id", Long.valueOf(params.firstGene))
				
					vector 
					{
						eq("type", "Integration (First)")
					}
				}
			}
			
			//old and slow
			//def recombinants = Recombinant.list().findAll{it.genes?.contains(Gene.get(params.firstGene)) && (it.vector.type == 'Integration (First)')}
	
			if(recombinants)
				render g.select(name: "firstRecombinant.id", from: recombinants, optionKey: "id")
			else render "No vector has been combined with this ${remoteLink(controller:"gene", action:"show", update:"body", id: params.firstGene){"gene"}} yet."
		}
		else render "Select a gene."
	}
	
	def updateSecondVector = {
		if(params.secondGene)
		{
			def recombinants = Recombinant.withCriteria{
				createAlias("genes", "g")
				eq("g.id", Long.valueOf(params.secondGene))
				
				vector {
					eq("type", "Integration (Second)")
				}
			}
			
			//old and slow
			//def recombinants = Recombinant.list().findAll{it.genes?.contains(Gene.get(params.secondGene)) && (it.vector.type == 'Integration (Second)')}
			
			if(recombinants)
				render g.select(name: "secondRecombinant.id", from: recombinants, optionKey: "id")
			else render "No vector has been combined with this ${remoteLink(controller:"gene", action:"show", update:"body", id: params.secondGene){"gene"}} yet."
		}
		else render "Select a gene."
	}
}
