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
package org.openlab.main

import org.openlab.application.*;

/**
 * MainController that every other controller should extend
 * @author markus.list
 *
 */
public abstract class MainController {

	def beforeInterceptor = [action:this.&checkActivate]
	def	applicationHandlerService;
	
	/**
	 * Checks whether the accessed application is active or not and blocks requests accordingly.
	 */
	//TODO Problem: A controller that does not extend this one is fully accessible even when application deactivated. A security filter would make more sense here.
	def checkActivate =	{
			if(this.hasProperty("application"))
			{
				if(applicationHandlerService.getApplicationByName(application.applicationName).isActivated())
					return true
			
				else 
				{
					render "<div class='body'><div class='errors'>Sorry, you're not authorized to view this page.</div></div>"
					return false
				}
			}
	}
}
