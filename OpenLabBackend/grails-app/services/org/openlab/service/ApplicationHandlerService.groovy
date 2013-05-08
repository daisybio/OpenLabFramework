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
package org.openlab.service;

import java.util.List;

import org.openlab.application.Application;

/**
 * Service that keeps track of registered applications and
 * their activation status.
 * @author markus.list
 *
 */
public class ApplicationHandlerService {

	static transactional = false
	
	def registeredApplications = []
	
	boolean registerApplication(Application app)
	{
		return registeredApplications.add(app);
	}
	
	boolean unregisterApplication(Application app)
	{
		return registeredApplications.remove(app);
	}
	
	List getRegisteredApplications()
	{
		return registeredApplications;
	}
	
	List getRegisteredApplicationNames()
	{
		def newList = []
		
		registeredApplications.each{
			newList << it.getApplicationName()
		}
		
		return newList
	}
	
	Application getApplicationByName(String name)
	{
		registeredApplications.findAll{ it.getApplicationName().equals(name) }[0]
	}
	
}
