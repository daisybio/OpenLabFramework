package org.openlab.search

import grails.converters.JSON
import org.apache.commons.collections.Predicate
import org.springframework.util.ClassUtils

class QuickSearchController {

    def settingsService
    def grailsApplication
    def searchableService

    def searchResultsAsJSON = {

        //first try exact search
        def resultMap = searchableService.search(params.query, params)
        println resultMap.total

        //if no results try * operator
        if (resultMap.total == 0) {
            resultMap = searchableService.search(params.query.toString() + "*", params)
        }

        def filteredResultMap = org.apache.commons.collections.CollectionUtils.select(resultMap.results, new Predicate() {
            public boolean evaluate(final Object dto) {
                return (dto.getClass().superclass == org.openlab.main.DataObject.class);
            }
        });

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

    def showResult = {
        //println params
        def splitArray = params.id.split(":")

        def controller = splitArray[0]
        def id = splitArray[1]

        params.bodyOnly = true
        redirect(controller: controller, action: "show", params: params, id: id)
    }
}
