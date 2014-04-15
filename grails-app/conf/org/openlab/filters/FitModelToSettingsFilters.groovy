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
package org.openlab.filters

class FitModelToSettingsFilters {

	def settingsService
	def springSecurityService
	
	def filters = {
		
		/**
		 * Is responsible for setting the param numberOfAddins according to the user settings
		 */
		numberOfAddinsFilter(controller: '*', action: '*')
		{
			after = { model ->
				if (springSecurityService.isLoggedIn()) 
				{
					def numOfAddins = settingsService.getUserSetting(key: "addins.number")
					if(numOfAddins && model)
						model.numberOfAddins = numOfAddins
				}
				return true;
			}
		}
		
		/**
		 * Is responsible to add the lang param according to user settings
		 */
		languageSettingFilter(controller: '*', action: '*')
		{
			before = {
				if(!params.lang && springSecurityService.isLoggedIn())
				{
					def defaultLanguage = settingsService.getUserSetting(key: "language")
					if(defaultLanguage)
					{
						params.lang = defaultLanguage
					}
				}
				
				return true;
			}
		}
	}
}
