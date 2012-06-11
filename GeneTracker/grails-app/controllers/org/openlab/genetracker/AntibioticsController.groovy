package org.openlab.genetracker

import grails.converters.*;
import org.openlab.data.*;

class AntibioticsController extends DataTableControllerTemplate{
	
	def scaffold = Antibiotics
	
	def antibioticsAsJSON = {
		
		def list = []
		
		CellLineData.get(params.id).antibiotics.each {
			list << [
				id: it.id,
				antibiotics: it.antibiotics.toString(),
				concentration: it.concentration,
				modifyUrls:
					remoteLink(before:"if(!confirm('Are you sure?')) return false", action:'removeTableRow', controller:'antibiotics', id: it.id, onSuccess: "javascript:GRAILSUI.dtAntibiotics.requery();"){"<img src=${createLinkTo(dir:'images/skin',file:'olf_delete.png')} alt='Delete' />"}
			]
		}
		
		render tableDataAsJSON(jsonList: list)
	}
	
	def tableDataChange = {
			def antibioticsWithC = AntibioticsWithConcentration.findById(params.id)
			
			if(params.field == "antibiotics")
			{
				antibioticsWithC.antibiotics = Antibiotics.findByLabel(params.newValue)
			}
			else antibioticsWithC."$params.field" = params.newValue
			
			antibioticsWithC.save()
			render "success"
	}
	
	def addTableRow = {
		def cellLineData = CellLineData.get(params.id)
		def unusedAntibiotics = (Antibiotics.list() - cellLineData.antibiotics.collect{it.antibiotics})
		
		if(unusedAntibiotics.size() > 0)
		{
			params.antibiotics = unusedAntibiotics[0]
			params.concentration = ""
			params.cellLineData = cellLineData
			
			def antibiotics = new AntibioticsWithConcentration(params)
			antibiotics.save(flush:true)
			render "success"
		}
		else render text:"error", status: 503
	}
	
	def removeTableRow = {
		AntibioticsWithConcentration.get(params.id).delete()
		render "success"
	}
}
