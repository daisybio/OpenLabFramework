package org.openlab.genetracker

import org.openlab.data.DataTableControllerTemplate;
import org.openlab.genetracker.vector.*;
import org.openlab.genetracker.*;
import org.openlab.data.*;

class RecombinantController extends DataTableControllerTemplate{

	def scaffold = Recombinant
	
	def recombinantsAsJSON = {
		
		def list = []
		//def gene = Gene.get(params.id)
		   
		def recombinants = Recombinant.withCriteria {
			createAlias("genes", "g")
			eq("g.id", Long.valueOf(params.id))
		} 
		
		//Recombinant.list().findAll{it.genes.contains(gene)}
		
		recombinants.each {
			list << [
				id: it.id,
				gene: it.genes.toString(),
				vector: it.vector.toString(),
				notes: it.notes.toString(),
				modifyUrls:
					(remoteLink(action:'show', controller:'recombinant', id: it.id, params: [bodyOnly:true], update:'body'){"<img src=${createLinkTo(dir:'images/skin',file:'olf_arrow_right.png')} alt='Show' />"}+
					remoteLink(before:"if(!confirm('Are you sure?')) return false", action:'removeTableRow', controller:'recombinant', id: it.id, onSuccess: "javascript:GRAILSUI.dtGeneVectors.requery();"){"<img src=${createLinkTo(dir:'images/skin',file:'olf_delete.png')} alt='Delete' />"})
			]
		}

	   render tableDataAsJSON(jsonList: list)
	}
	
	def tableDataChange = {
			
			def recombinant = Recombinant.findById(params.id)
	
			//parse username
			if(params.field == "vector") recombinant.vector = Vector.findByLabel(params.newValue)
			
			else recombinant."${params.field}" = params.newValue
						
			if(recombinant.save(flush:true))
			{
				render "success"
			}
			else
			{
				render text: "Error changing data.", status: 503
			}
	}
	
	def addTableRow = {
		def gene = Gene.get(params.id)
		
		def vectors = Vector.findAllByTypeNotEqual("Acceptor")
		
		if(vectors.size()>0) 
			
			params.vector = vectors.get(0) 
		
		params.notes = ""
		params.genes = [gene]
			
		def recombinant = new Recombinant(params)
		
		if(recombinant.hasErrors())
		{
			recombinant.errors.each{
				log.error(it.toString())
			}
		}
		if(recombinant.save(flush:true))
		{	
			render "success"
		}
		
		else render text: "failure", status: 503
	}
	
	def removeTableRow = {
		def recombinant = Recombinant.get(params.id).delete()
		render "success"
	}

}
