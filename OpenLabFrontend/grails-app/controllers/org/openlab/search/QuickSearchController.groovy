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

import grails.converters.JSON
import org.apache.commons.collections.Predicate
import org.springframework.util.ClassUtils
import org.openlab.main.DataObject
import org.apache.commons.collections.CollectionUtils

class QuickSearchController {

    def settingsService
    def searchableService
    def barcodeBuilderService

    def searchResultsAsJqueryJSON = {

        if(barcodeBuilderService && params.query.toString().length() == 16)
        {
            def barcodeResultMap = checkForBarcode()
            if(barcodeResultMap){
                def jsonList = [[value: barcodeResultMap.id, label: barcodeResultMap.label]]
                render jsonList as JSON
                return
            }
        }
        def filteredResultMap = getResultMap()

        def jsonList = filteredResultMap.collect {
            def className = ClassUtils.getShortName(it.getClass())
            def controllerName = className[0].toLowerCase() + className[1..-1]
            [value: (controllerName + ":" + it.id), label: (className + ":" + it.toString())]
        }

        render jsonList as JSON
    }

    def searchResultsAsJSON(){

        def resultMap

        //first try barcode search
        if(barcodeBuilderService && params.query.toString().length() == 16)
        {
            resultMap = checkForBarcode()
            if(resultMap){

                def jsonResult =  [
                        results: resultMap
                ]

                render jsonResult as JSON
                return
            }

        }

        def filteredResultMap = getResultMap()

        def jsonList = filteredResultMap.collect {
            def className = ClassUtils.getShortName(it.getClass())
            def controllerName = className[0].toLowerCase() + className[1..-1]
            [id: (controllerName + ":" + it.id), label: (className + ":" + it.toString())]
        }

        def jsonResult = [
                results: jsonList
        ]

        render jsonResult as JSON

    }

    private def checkForBarcode() {
        def resultMap
        def barcode
        try {
            barcode = barcodeBuilderService.buildBarcodeFromId(params.query)
            flash.message = "Scanned: " + barcode.text + " (" + barcode.description + ")"
        } catch (Exception e) {
            log.error e.getMessage()
        }
        if (barcode) {
            def controllerName = DataObject.get(barcode.dataObject.id).getType().toString()
            resultMap = [id: controllerName + ":" + barcode.dataObject.id, label: controllerName.capitalize() + ":" + barcode.text]
        }
        resultMap
    }


    private Collection getResultMap() {
        def resultMap
        //first try exact search
        resultMap = searchableService.search(params.query, params)

        //if no results try * operator
        if (resultMap.total == 0) {
            resultMap = searchableService.search(params.query.toString() + "*", params)
        }

        def filteredResultMap = CollectionUtils.select(resultMap.results, new Predicate() {
            public boolean evaluate(final Object dto) {
                return (dto.getClass().superclass == DataObject.class);
            }
        });
        filteredResultMap
    }

    def showResult = {
        //println params
        def splitArray = params.id.split(":")

        def controller = splitArray[0]
        def id = splitArray[1]

        params.bodyOnly = true
        redirect(controller: controller, action: "show", params: params, id: id)
    }
}
