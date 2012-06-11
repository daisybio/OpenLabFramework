package org.openlab.genetracker

import grails.converters.*;
import org.openlab.data.*;

/**
 * DataTableController for _passageTab.gsp
 * @author markus.list
 *
 */
class PassageController extends DataTableControllerTemplate{

	def springSecurityService
	def grailsUITagLibService
	def settingsService
	
	def scaffold = Passage

	def show = {
		redirect(controller: "cellLineData", action: "show", id: Passage.get(params.id).cellLineData.id, params: params)
	}
	
	def passagesAsJSON = {
		    
		    def list = []
		                       
		    Passage.findAllByCellLineData(CellLineData.get(params.id)).each {
				list << [
				    id: it.id,
	                passageNr: it.passageNr,
				    researcher: it.researcher.username,
	                notes: it.notes,
	                date: grailsUITagLibService.dateToJs(it.date),
	                modifyUrls:
	                	remoteLink(before:"if(!confirm('Are you sure?')) return false", action:'removeTableRow', controller:'passage', id: it.id, onSuccess: "javascript:GRAILSUI.dtPassages.requery();"){"<img src=${createLinkTo(dir:'images/skin',file:'olf_delete.png')} alt='Delete' />"}
	            ]
			}   	
		    
		    render tableDataAsJSON(jsonList: list)
	}
	
    def tableDataChange = {
	        def passage = Passage.findById(params.id)

	        if(params.field == "researcher") passage.researcher = org.openlab.security.User.findByUsername(params.newValue)
	        else if(params.field == "date")
	        {
	        	def simpleDateFormat = new java.text.SimpleDateFormat("E MMM dd yyyy HH:mm:ss ZZZZZZZ", Locale.ROOT);

	        	Date date = simpleDateFormat.parse(params.newValue)
	        	passage.date = date
	        }
        	else passage."$params.field" = params.newValue
	        
        	passage.save()
	        render "success"
    }
	
	def addTableRow = {
		def cellLineData = CellLineData.get(params.id)
		
		params.researcher = currentUser()
		params.notes = ""
		params.passageNr = settingsService.getSetting(key: "label.passageNr")?:"pxx/xx/xx/xx"
	    params.date = dateNow()
	    params.cellLineData = cellLineData
	    
    	def passage = new Passage(params)
		passage.save(flush:true)
    	render "success"
	}
	
	def removeTableRow = {
		Passage.get(params.id).delete()
		render "success"			
	}
	
}
