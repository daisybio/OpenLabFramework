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

import org.openlab.security.*;
import org.openlab.main.*;

/**
 * Scaffolded controller to CRUD on projects.
 * @author markus.list
 *
 */
class ProjectController extends MainController {

	def springSecurityService
	
    def scaffold = Project
	
	def listMyProjects = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.action = "list"
		def myProjects = Project.findAllByCreator(User.findByUsername(getUserName()));
		
		render(view:"list", model: [projectList: myProjects, projectTotal: myProjects.count()])
	}
    
	def listOtherProjects = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def myProjects = Project.findAllByCreator(User.findByUsername(getUserName()));
		def otherProjects = Project.list() - myProjects
		 
		render(view:"list", model: [projectList: otherProjects, projectTotal: otherProjects.count()])
	}
	
	def getUserName()
	{
		def principal = springSecurityService.getPrincipal()
		principal.getUsername()
	}
}