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
package org.openlab.storage

class BoxCreationService {

	def settingsService
	
	def getSortedBoxElements(def id)
	{
		def box = Box.get(id)
		
		def xdim = box.xdim
		def ydim = box.ydim
		def elements = box.elements
		
		//sort elements for easy iteration
		def sortedElements = elements.sort{ a, b ->
			if(a.xcoord == b.xcoord) a.ycoord <=> b.ycoord
			else a.xcoord <=> b.xcoord
		}
		
		sortedElements
	}
	
	def getAxisLabel(int num, def axis, def isAlpha = false)
	{
		num += 1
		if(isAlpha == false) isAlpha = settingsService.getUserSetting(key: "storage.axis.${axis}") == "alpha"
		
		if(isAlpha)
		{
			 StringBuilder sb = new StringBuilder();
			 char a = 'A'
			 
			 while (num > 0) {
				 char label = a + (num % 26)-1
				 sb.append(label);
				 num /= 26;
			 }

			 return sb.reverse().toString();
		}
		else
		{
			return num
		}
	}
	
	def getIntFromAlpha(char c)
	{
		def num = ((int) c) - ((int)'A') + 1 
		return num
	}

}
