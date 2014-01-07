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
