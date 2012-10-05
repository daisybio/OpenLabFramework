package org.openlab.main

import org.apache.commons.lang.StringUtils
import org.openlab.security.*;
import grails.converters.*;

/**
 * Controller for DataObjects in general
 * @author markus.list
 *
 */
class DataObjectController extends MainController {
	
	def scaffold = true

	/**
	 * Can be called with id of an DataObject. The actual object
	 * type is then determined and it is redirected to 
	 * the corresponding controller and action. 
	 */
	def showSubClass =
	{
		flash.message = flash.message
		
		if(params.id)
		{
			long id
			try{
				id = Integer.valueOf(params.id)
			}catch(NumberFormatException e)
			{
				log.error "could not parse id to long"
				return redirect(controller: "project", action: "list")
			}
			def objType = new String(DataObject.get(id).getType())
		
			if(!params.bodyOnly) params.bodyOnly = true
		
			redirect(controller: objType, action: "show", params:params)
		}
		else
		{
			redirect(controller: "project", action: "list")
		}
	}
	
	/**
	 * methods for XML or JSON representation (RESTful Webservice)
	 * At the moment for demonstration only (22.06.2010).
	 */
    def xmlList = {
        if(domainClass)
        	render domainClass.list() as XML
	}

    def xmlShow = {
        if(domainClass)
			render domainClass.get(params.id) as XML
	}
	
    def jsonList = {
        if(domainClass)
	        	render domainClass.list() as JSON
		}

    def jsonShow = {
		if(domainClass)
				render domainClass.get(params.id) as JSON
		}
}
