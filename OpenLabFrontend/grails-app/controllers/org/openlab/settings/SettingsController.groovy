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
		println params
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
        params.max = Math.min(params.max?params.int('max'):10, 100)
		[settingsList: UserSetting.list(params), settingsTotal: UserSetting.count()]
	}
	
	/**
	 * Settings filtered by a starting query. Results are returned as JSON. Used to fill 
	 * autocomplete box with choices.
	 */
	def settingsAsJSON = {
	    def jsonList = UserSetting.findAllByKeyIlikeOrValueIlike("%${params.query}%", "%${params.query}%").collect{["KEY": it.key]}
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
	def deleteKey = {
		settingsService.deleteSetting(key: params.keyToDelete)
        params.remove("keyToDelete")
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
