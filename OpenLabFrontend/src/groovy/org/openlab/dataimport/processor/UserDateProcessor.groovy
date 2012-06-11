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
class UserDateProcessor extends GeneTrackerImportClass{

	def springSecurityService = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("springSecurityService")
	
	/*
	* Migrate users and sets default password 'password'
	*/
   def createUsers(){
	   
	   log.info "creating Users..."
	   
	   String password = springSecurityService.encodePassword('password')
	   
	   importer.getUsers().each{
		   def userParams = [:]
		   userParams.userRealName = "" + it.firstName + " " + it.lastName
		   userParams.username = it.username
		   userParams.email = it.email
		   userParams.enabled = true
		   userParams.password = password
		   
		   new User(userParams).save()
	   }
   }
	
	/**
	* Excel-import uses Joda-Time instead of Java-Time. Therefore we have this method for parsing purposes.
	* @param localDate
	* @return java.util.Date
	*/
   def parseJodaToJavaDate(def localDate)
   {
	   if(localDate == null || localDate == "NULL" || localDate == "null") return new Date()
	   def dateTime = localDate.toDateTime(new DateTime());
	   dateTime.toDate();
   }
   
   def setDateAndUserParams(def paramMap)
   {
	   def users = importer.getUsers()
	   
	   def insertUsername = users.find{it.oldId == paramMap.insertUserOldId}?.username
	   def editUsername = users.find{it.oldId == paramMap.editUserOldId}?.username
	   def admin = User.findByUsername("admin")
	   
	   if(insertUsername)
		   paramMap.creator = User.findByUsername(insertUsername)?:admin
	   else paramMap.creator = admin
	   
	   if(editUsername)
		   paramMap.lastModifier = User.findByUsername(editUsername)?:admin
	   else paramMap.creator = admin
	   
	   if(!paramMap.dateCreated) paramMap.dateCreated = new Date()
	   else paramMap.dateCreated = parseJodaToJavaDate(paramMap.dateCreated)
	   
	   if(!paramMap.lastUpdate) paramMap.lastUpdate = new Date()
	   else paramMap.lastUpdate = parseJodaToJavaDate(paramMap.lastUpdate)
	   
	   return paramMap
   }

	
}
