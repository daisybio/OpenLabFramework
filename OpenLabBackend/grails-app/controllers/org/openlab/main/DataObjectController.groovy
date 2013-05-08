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
