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

import java.util.ArrayList;
import org.openlab.module.*;
import grails.spring.BeanBuilder

/**
 * Class to handle everything concerning modules. A module is a business logic representation of
 * a domain class having interactions with DataObjects.
 * @author markus.list
 */
public class ModuleHandlerService {

	static transactional = false

	def grailsApplication
	
	/*
	 * returns all modules that claim to have an interest in the given domain class
	 */
	def getInterestedModules = { attrs ->
		def modules = grailsApplication.getModuleClasses().collect { 
			it.referenceInstance
		}
		
		modules = modules.findAll { it instanceof Module}
		
		modules.findAll { it.isInterestedIn(attrs.domainClass, attrs.type) }
	}

    def getInterestedMobileModules = { attrs ->
        def modules = grailsApplication.getModuleClasses().collect {
            it.referenceInstance
        }

        modules = modules.findAll { it instanceof Module}

        modules.findAll { it.isInterestedIn(attrs.domainClass, attrs.type) && it.isMobile() }
    }


    def getMenuModules = {
		def modules = grailsApplication.getModuleClasses().collect { 
			it.referenceInstance
		}
		
		modules.findAll { it instanceof MenuModule}
	}
	
	def getAddinModules = {
		def modules = grailsApplication.getModuleClasses().collect {
			it.referenceInstance
		}
		
		modules.findAll{ it instanceof AddinModule }
	}
	
}
