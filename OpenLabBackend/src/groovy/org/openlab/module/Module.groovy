package org.openlab.module

interface Module 
{	
	/**
	 * Name of the plugin needed to resolve correct template path
	 */
	def getPluginName()
	
	def getTemplateForDomainClass(def domainClass)
	
	def isInterestedIn(def domainClass, def type)
	
	def getModelForDomainClass(def domainClass, def id)
}
