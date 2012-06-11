package org.openlab.genetracker

import java.util.Date;
import org.openlab.security.User;
import grails.converters.*;
import org.openlab.data.*;
import org.openlab.genetracker.*;

/**
 * DataTableController for _viabilityTab.gsp
 * @author markus.list
 *
 */
class ViabilityController extends DataTableControllerTemplate {
	
	def scaffold = Viability
	def RecombinantsService
	
	def show = {
		redirect(controller: "cellLineData", action: "show", id: Viability.get(params.id).cellLineData.id, params: [bodyOnly:true])
	}
	
	def viabilityAsJSON = {
		    
		    def list = []
		                       
		    Viability.findAllByGene(Gene.get(params.id)).each {
				list << [
				    id: it.id,
				    cellLineData: (it.cellLineData.id + ":" + it.cellLineData.toString()),
				    researcher: it.researcher.username,
	                notes: it.notes,
	                date: grailsUITagLibService.dateToJs(it.date),
	                percentage: it.percentage,
	                modifyUrls:
	                	remoteLink(before:"if(!confirm('Are you sure?')) return false", action:'removeTableRow', controller:'viability', id: it.id, onSuccess: "javascript:GRAILSUI.dtViability.requery();"){"<img src=${createLinkTo(dir:'images/skin',file:'olf_delete.png')} alt='Delete' />"}
	            ]
			}

	       render tableDataAsJSON(jsonList: list)
	}
	
    def tableDataChange = {
	        
			def viability = Viability.findById(params.id)

			//parse username
	        if(params.field == "researcher") viability.researcher = org.openlab.security.User.findByUsername(params.newValue)
	        
	        //split new value to obtain id (id:stringName)
	        else if(params.field == "cellLineData")
	        {
	        	def splitArray = params.newValue.split(':')
	        	viability.cellLineData = CellLineData.get(splitArray[0])
	        }
	        
	        //parse date
	        else if(params.field == "date")
	        {
	        	viability.date = parseDate()
	        }
			
			//parse double
	        else if(params.field == "percentage")
	        {
	        	try{
	        		viability.percentage = Double.valueOf(params.newValue)
	        	}catch(NumberFormatException e)
	        	{
	        		return false
	        	}
	        }
        	else viability."$params.field" = params.newValue
	        
        	viability.save()
	        render "success"
    }
	
	def addTableRow = {
		def gene = Gene.get(params.id)
		def dateNow = new Date(new java.util.Date().getTime())
		
		def cellLineData = RecombinantsService.recombinantsWithGene(params.id)
		
		//def cellLineData = CellLineData.list().findAll{it.firstRecombinant.genes.contains(gene) || it.secondRecombinant?.genes.contains(gene)}
		
		if(cellLineData)
		{
			params.researcher = currentUser()
			params.notes = ""
			params.percentage = 0
		    params.date = dateNow
		    params.gene = gene
		    params.cellLineData = cellLineData.iterator().next()
		    
	    	def viability = new Viability(params)
			viability.save(flush:true)
	    	render "success"
		}
		
		else render "failure"
	}
	
	def removeTableRow = {
		Viability.get(params.id).delete()
		render "success"			
	}
}
