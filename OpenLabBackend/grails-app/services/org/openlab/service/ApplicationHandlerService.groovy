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
