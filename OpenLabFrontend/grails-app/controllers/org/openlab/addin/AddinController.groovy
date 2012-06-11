package org.openlab.addin

/**
 * Controller handling all actions concerning addins.
 * Addins are portlet style widgets located on a sidebar.
 * @author markus.list
 *
 */
class AddinController {

	def settingsService
	
	/**
	 * if user drags an addin to another slot, the contens are swapped 
	 */
	def swapPositions = { 
		
		//get slot ids
		def drag = params.drag
		def drop = params.drop.split('_')[2]
		
		if(drag != drop)
		{
			//get slot settings
			def dragSetting = settingsService.getUserSetting(key:"addins.slot.${drag}")
			def dropSetting = settingsService.getUserSetting(key:"addins.slot.${drop}")
			
			//persist new settings 
			if(dropSetting) settingsService.setUserSetting(key: "addins.slot.${drag}", value: dropSetting)
			//empty slot as target
			else settingsService.setUserSetting(key: "addins.slot.${drag}", value: "empty slot")
			
			settingsService.setUserSetting(key: "addins.slot.${drop}", value: dragSetting)
		}
		render "success"
	}
}
