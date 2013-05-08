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
package org.openlab.taglib;

import org.openlab.genetracker.*;

class AddinsTagLib {
	
	def settingsService
	def moduleHandlerService
	
	static namespace = 'addin'	
		
	def layoutAddins = { attrs, body ->
		out << "<table style='width: 250px; border: none;'>"
        def numberOfViews = settingsService.getUserSetting(key: "addins.number")

		for(int i = 1; i <= Integer.valueOf(numberOfViews); i++)
		{
			out << "<tr><td>" + richui.portletView(id:"${i}", slotStyle:'width: 250px; height: 200px;', playerStyle:'width: 250px; height: 200px;'){contentAtPos(slot:i);}
			out << "</td></tr>"
		}
		out << "</table>"
	}
	
	def contentAtPos = { attrs ->
		
		def i = attrs.slot
		
		def content = settingsService.getUserSetting(key: "addins.slot.${i}")
		
		if(content)
		{
			def addins = moduleHandlerService.getAddinModules()
			if(!addins)return content
			
			def addin = addins.find{it.getName().toString() == content.toString()}

			if(addin)
			{
				String r = g.render(template: "/addins/${addin.getTemplate()}", plugin: addin.getPluginName(), model: [slot: "${i}"])
				out << r
			}			
		
			else
			{
				 out << content.toString()
			}
		}
		else
		{
			out << "empty slot"
		}
	}
}
