package org.openlab.settings

import grails.converters.JSON

/**
 * Controller to render settings page
 * @author markus.list
 *
 */
class SettingsController {

	def settingsService
	def searchableService
	def moduleHandlerService
	
	def reindex = {
		searchableService.reindex()
		flash.message = "Reindex of domain classes for search running in background."
		redirect(action:"list", params: params)
	}
	
	
	def index = {
		redirect action: "list" 
	}
	
	/**
	 * When a key is selected via autocomplete, a listener calls upon this action to show 
	 * the corresponding result
	 */
	def updateShowResult = {
		def value = settingsService.getSetting(key: params.key)
		
		render (g.editInPlace(id: params.key, url: [action: 'editField', id: params.key], rows: 1, cols: 10, paramName: "value")
		{
			"${value}"
		})
	}
	
	/**
	 * Get all settings and filter for pagination and obey namespace restriction (e.g. filter for default)
	 */
	def list = {
		def dataset = settingsService.getSettingsDataSet()

		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def startRange = Math.min(dataset.rows().size()-1, params.offset? params.int('offset') : 0)
		def endRange = Math.min(dataset.rows().size()-1, startRange + params.max) 
        
		[settingsList: dataset.rows()[startRange..endRange], settingsTotal: dataset.rows().size()]	
	}
	
	/**
	 * Settings filtered by a starting query. Results are returned as JSON. Used to fill 
	 * autocomplete box with choices.
	 */
	def settingsAsJSON = {
	    def jsonList = settingsService.getSettingsDataSet().rows().findAll{it.key.startsWith(params.query)}
	    def jsonResult = [
	        settings: jsonList
	    ]
	    render jsonResult as JSON
	}
	
	/**
	 * Provides in-line editing capabilities. New values are saved to the
	 * database and rendered back to the page.
	 */
    def editField = {
		settingsService.setSetting(key: params.editorId, value: params.value)
		render params.value.encodeAsHTML()
    }
	
	/**
	 * Settings can be deleted.
	 */
	def delete = {
		settingsService.deleteSetting(key: params.key)
		params.remove "key"
		params.bodyOnly = true
		redirect action: list, params: params 
	}
	
	/**
	 * New settings can be added.
	 */
	def add = {
		settingsService.setSetting(key: params.key, value: params.value)
		redirect action:list
	}
}
