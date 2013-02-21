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
