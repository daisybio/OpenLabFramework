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
package org.openlab.search

import org.compass.core.engine.SearchEngineQueryParseException
import org.apache.lucene.queryParser.ParseException
import org.openlab.main.DataObject
import grails.converters.JSON

class FullSearchController {
	
    def searchableService
    def barcodeBuilderService


    /**
     * Index page with search form and results
     */
    def index = {
        //first try barcode search
        if(barcodeBuilderService && params.q.toString().length() == 16)
        {
            def barcode
            try{
                barcode = barcodeBuilderService.buildBarcodeFromId(params.q)
                flash.message = "Scanned: " + barcode.text + " (" + barcode.description + ")"
            }catch(Exception e){
                log.error e.getMessage()
            }
            if(barcode)
            {
                def controllerName = DataObject.get(barcode.dataObject.id).getType().toString()
                redirect(controller: controllerName, action: "show", id: barcode.dataObjectId, params: ['bodyOnly':true])
                return
            }

        }

        if (!params.q?.trim()) {
			if(session.lastQuery && params.backLink)
				params.q = session.lastQuery
			else return [:]
        }
		else
			session.lastQuery = params.q
        
		try {
            if(params.q.toString().contains("?") || params.q.toString().contains("*") && params?.suggestQuery)
				params.remove("suggestQuery")
            if(params.q.toString().contains(":"))
            {
                try{
                    params.q = params.q.toString().substring(params.q.toString().indexOf(":")+1, params.q.toString().length())
                } catch(Exception e)
                {
                    log.warn "Could not truncate search string: ${e.getMessage()}"
                }
            }
			return [searchResult: searchableService.search(params.q, params)]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
		
    }

    /**
     * Perform a bulk index of every searchable object in the database
     */
    def indexAll = {
        Thread.start {
            searchableService.index()
        }
        render("bulk index started in a background thread")
    }

    /**
     * Perform a bulk index of every searchable object in the database
     */
    def unindexAll = {
        searchableService.unindex()
        render("unindexAll done")
    }
}
