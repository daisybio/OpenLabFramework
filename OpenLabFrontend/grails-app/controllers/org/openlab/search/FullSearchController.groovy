package org.openlab.search

import org.compass.core.engine.SearchEngineQueryParseException
import org.apache.lucene.queryParser.ParseException

class FullSearchController {
	
    def searchableService

    /**
     * Index page with search form and results
     */
    def index = {
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
