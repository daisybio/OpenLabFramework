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
