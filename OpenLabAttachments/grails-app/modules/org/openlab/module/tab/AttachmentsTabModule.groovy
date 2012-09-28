package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.genetracker.*;
import org.codehaus.groovy.grails.commons.ApplicationHolder

class AttachmentsTabModule implements Module{

	def grailsApplication = ApplicationHolder.application
	
	def getPluginName() {
		"open-lab-attachments"
	}

	def getTemplateForDomainClass(def domainClass)
	{
		return "attachmentsTab"
	}
	
	def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && grailsApplication.getArtefactByLogicalPropertyName("Domain", domainClass)?.getClazz().superclass == org.openlab.main.DataObject) return true
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		def dataObject = org.openlab.main.DataObject.get(id)
		
		def attachments = openlab.attachments.DataObjectAttachment.withCriteria {
			createAlias("dataObjects", "dos")
			eq("dos.id", Long.valueOf(id))
		}
		return [dataObjectAttachmentInstanceList: attachments, dataObjectAttachmentInstanceTotal: attachments?.size()?:0]
	}
}