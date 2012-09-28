package org.openlab.data

import java.util.Date;
import org.openlab.security.User;
import grails.converters.*;

/**
 * Template that must be extended by Controllers that want to
 * feed a GRAILS-UI dataTable with JSON data.
 * Allows for pagination, sorting, addition and deletion of rows
 * if extended properly.
 * @author markus.list
 *
 */
abstract class DataTableControllerTemplate {

	def springSecurityService
	def grailsUITagLibService
	def settingsService
	
	/**
	 * Translates a list of property lists to JSON
	 * Considers pagination and sorting parameters
	 */
	def tableDataAsJSON = { attrs -> 
		   	
			def list = attrs.jsonList
		    
			//if there are no elements return no records
			//treat differently because list[range] (below) can throw
			//index out of bound exception
		   if (list.size() == 0)
		   {
			   def data = [totalRecords: 0, results: list] 
               render data as JSON 
		   }
			
		    //there are elements, proceed
		   else
		   {
	        def offset = params.integer("offset")
	        def max = params.integer("max")
	        def endRange = (offset + max -1) <= (list.size()-1) ? (offset + max -1) : list.size()-1
	        def range = offset..endRange
	        def result = list[range].sort{it."${params.sort}"}
	        if (params.order == "desc") Collections.reverse(result)
		    
		    response.setHeader("Cache-Control", "no-store")
		    	    
		    def data = [
		            totalRecords: list.size(),
		            results: result
		    ]
		    render data as JSON
		   }
	}
	
	/**
	 * Parse date from JSON format back to Java date
	 */
	def parseDate = {
		def simpleDateFormat = new java.text.SimpleDateFormat("E MMM dd yyyy HH:mm:ss ZZZZZZZ", Locale.ROOT);

    	simpleDateFormat.parse(params.newValue)
	}
	
	/**
	 * Dummy closure, must be implemented
	 */
	abstract def tableDataChange;
	
	def dateNow = {
		new Date(new java.util.Date().getTime())
	}
	
	/**
	 * Returns the current user
	 */
	def currentUser = {
        def principal = springSecurityService.getPrincipal()
        def userId = principal.id
        org.openlab.security.User.get(userId)
	}
	
	/**
	 * dummy method, must be implemented
	 */
	abstract def addTableRow;		
	
	/**
	 * dummy method, must be implemented
	 */
	abstract def removeTableRow;
}
