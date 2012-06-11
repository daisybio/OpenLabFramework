package org.openlab.main

import org.openlab.application.*;

/**
 * MainController that every other controller should extend
 * @author markus.list
 *
 */
public abstract class MainController {

	def beforeInterceptor = [action:this.&checkActivate]
	def	applicationHandlerService;
	
	/**
	 * Checks whether the accessed application is active or not and blocks requests accordingly.
	 */
	//TODO Problem: A controller that does not extend this one is fully accessible even when application deactivated. A security filter would make more sense here.
	def checkActivate =	{
			if(this.hasProperty("application"))
			{
				if(applicationHandlerService.getApplicationByName(application.applicationName).isActivated())
					return true
			
				else 
				{
					render "<div class='body'><div class='errors'>Sorry, you're not authorized to view this page.</div></div>"
					return false
				}
			}
	}
}
