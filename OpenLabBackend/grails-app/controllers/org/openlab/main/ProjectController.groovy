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