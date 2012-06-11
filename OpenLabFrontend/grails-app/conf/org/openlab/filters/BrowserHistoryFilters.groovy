package org.openlab.filters
;

import org.openlab.main.*;

/**
 * This class contains filters designed for browser history management
 * @author markus.list
 *
 */
public class BrowserHistoryFilters {
	
	def filters = {
			
			/**
			 * Adds information to the browser session that is used for creation of the backlink.
			 * The current controller is set as the next back controller. Before it is set, the
			 * previous nextBackController ist set as current backController.
			 * 
			 * Example: 
			 * Initially /controllerA/show/2 is called and the information is stored as 
			 * nextBackController, Action and Id.
			 * 
			 * Now /controllerB/show/6 is called. ControllerA, show and 2 are stored as current
			 * backController, action and id. controllerB, show and 6 are stored as next BackController,
			 * Action and Id. 
			 * 
			 * If you now hit the back button you get back to controllerA. If you browser one site further
			 * you would get back to controllerB.
			 * 
			 * This means that you always can go only one step back in history, which is still better than nothing.
			 * The idea could be pushed further by implementing YUI Java Script based Browser History 
			 * Manager.
			 */
			simpleAjaxHistoryTracker(controller: '*', action: '(show|list)')
			{				
				afterView = {
					session.backController = session.nextBackController
					session.backAction = session.nextBackAction
					session.backId = session.nextBackId
					session.nextBackController = params.controller
					session.nextBackAction = params.action
					session.nextBackId = params.id
					
					//return true
				}
			}
			
			simpleAjaxHistoryTrackerForFullSearch(controller: 'fullSearch', action: 'index')
			{
				afterView = {
					session.backController = session.nextBackController
					session.backAction = session.nextBackAction
					session.backId = session.nextBackId
					session.nextBackController = params.controller
					session.nextBackAction = params.action
					session.nextBackId = params.id
					session.lastQuery = params.q
					//return true
				}
			}
	}
}
