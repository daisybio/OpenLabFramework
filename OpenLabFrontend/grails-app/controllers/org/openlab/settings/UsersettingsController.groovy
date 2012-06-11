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
}
