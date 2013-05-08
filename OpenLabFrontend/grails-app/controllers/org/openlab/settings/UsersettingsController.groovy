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

class UsersettingsController {

	def settingsService
	def moduleHandlerService
	
	def addins = {
		def numberOfAddins = 4
		
		try{
			numberOfAddins = Integer.valueOf(settingsService.getUserSetting(key: "addins.number")?:4)
		}catch(NumberFormatException e)
		{
			log.warn "NumberFormatException when parsing number of addins."
		}
		def allAddins = moduleHandlerService.getAddinModules().collect{it.getName()}
		def currentAddins = []
		
		for(int i = 1; i <= numberOfAddins; i++)
		{
			currentAddins << settingsService.getUserSetting(key: "addins.slot.${i}")
		}
		
		[numberOfAddins:numberOfAddins, currentAddins: currentAddins, allAddins: allAddins]
	}
	
	def numOfAddinsChanged = {
		if(params.numOfAddins)
		{
			settingsService.setUserSetting(key: "addins.number", value: params.numOfAddins)
		}
		render(template: "/layouts/selectAddins", model: addins())
	}
	
	def changeAddin = {
		if(params.id && params.newValue)
		{
			settingsService.setUserSetting(key: "addins.slot.${params.id}", value: params.newValue)
		}
		
		render "Slot ${params.id} now hosts ${params.newValue} as Addin."
	}
	
	def user = {
		def userLanguage = settingsService.getUserSetting(key: "language")
		[languages:["de", "da", "en", "es", "it", "nl", "pt", "th", "fr", "ja" , "ru", "zh"], userLanguage: userLanguage]
	}
	
	def changeLanguage = {
		if(params.lang)
		{
			 settingsService.setUserSetting(key: "language", value: params.lang)
			 render "Language changed to ${params.lang}."
		}
		else render "Could not change language setting."
		
	}

    def collapseLeftColumn(){
        settingsService.setUserSetting(key: "left.column.collapse", value: "true")
        render "ok"
    }

    def collapseRightColumn(){
        settingsService.setUserSetting(key: "right.column.collapse", value: "true")
        render "ok"
    }

    def expandLeftColumn(){
        settingsService.setUserSetting(key: "left.column.collapse", value: "false")
        render "ok"
    }

    def expandRightColumn(){
        settingsService.setUserSetting(key: "right.column.collapse", value: "false")
        render "ok"
    }

}
