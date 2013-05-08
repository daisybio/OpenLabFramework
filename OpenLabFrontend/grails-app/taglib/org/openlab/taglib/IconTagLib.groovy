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
package org.openlab.taglib

import java.util.Calendar;

class IconTagLib {

	def loadIcon = { attrs ->
		
		if(isChristmas()) 
		{
			out << """<link rel="shortcut icon" href="${resource(dir:'images',file:'christmas.gif')}" type="image/x-icon" />"""
		}
		else
		{ 
			out << """<link rel="shortcut icon" href="${resource(dir:'images',file:'openlab_ico.gif')}" type="image/x-icon" />"""
		}	
	}
	
	def loadLogo = { attrs ->
		
		if(isChristmas())
			out << """<img src="${createLinkTo('dir': 'images', file: 'christmas.gif')}" />"""
		
		else
			out << """<img src="${createLinkTo('dir': 'images', file: 'OLFt.gif')}" />"""
			
		if(isHolyNight())
		{
			out << """<script type="text/javascript">alert('Merry Christmas!');</script>"""
		}
	}
	
	def isHolyNight()
	{
		def cal = new java.util.GregorianCalendar()
		
		(cal.get(Calendar.MONTH) == 11 && cal.get(Calendar.DAY_OF_MONTH) == 24)
	}
	
	def isChristmas()
	{
		def cal = new java.util.GregorianCalendar()
		
		def dezRange = 24..31
		def janRange = 1..6
		
		((cal.get(Calendar.MONTH) == 11 && dezRange.contains(cal.get(Calendar.DAY_OF_MONTH)))
		||(cal.get(Calendar.MONTH) == 0 && janRange.contains(cal.get(Calendar.DAY_OF_MONTH))))
	}
	
}
