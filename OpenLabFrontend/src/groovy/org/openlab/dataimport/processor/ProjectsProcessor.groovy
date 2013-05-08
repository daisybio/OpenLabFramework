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
package org.openlab.dataimport.processor

import org.openlab.security.*;
import org.openlab.main.*;
import org.openlab.genetracker.*;
import org.openlab.genetracker.vector.*;
import org.openlab.storage.*;
import org.joda.time.*;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.openlab.dataimport.processor.*;
import org.openlab.dataimport.*;
import org.openlab.dataimport.creator.*;
import groovy.transform.InheritConstructors

@InheritConstructors
class ProjectsProcessor extends GeneTrackerImportClass{
	
	static def odenseProjects = ["NanoCAN", "Defensins", "IFIT", "metastases", "colorectal", "urgent metastases"]
	
	/*
	* Migrates Projects. Parses dates where possible or sets current date instead.
	* @depends users
	*/
   def createProjects(){
	   
	   log.info "creating Projects..."
	   
	   def odenseLab = Laboratory.findByName("Odense")
	   def heidelbergLab = Laboratory.findByName("Heidelberg")
	   
	   importer.getProjects().each{ project ->

		   project = new UserDateProcessor(importer).setDateAndUserParams(project)
		   
		   if(!project.description) project.description = "Imported project without description"
		   if(!project.name) project.name = "Import " + Project.list().findAll{it.name.startsWith "Import"}.size().toString()
		  
		   def laboratory
		   
		   if(odenseProjects.contains(project.name)) laboratory = odenseLab
		   else laboratory = heidelbergLab
		   
		   laboratory.addToProjects(new Project(project)).save(flush:true)
		   
		   if(laboratory.hasErrors())
		   {
			   laboratory.errors.each{
				   log.error it.toString()
			   }
		   }
		   
	   }
   }
   
   def findProjectForGene(def geneId)
   {
	   if(!geneId) return null
	   
	   def projectGenes = importer.getProjectGenes()
	   def projects = importer.getProjects()
	   def projectId = projectGenes.find{it.geneId == geneId}?.projectId
	   
	   def projectName = projects.find{it.oldId == projectId}?.name
	   
	   if(projectName)
		   Project.findByName(projectName)
	   else return null
   }
}
