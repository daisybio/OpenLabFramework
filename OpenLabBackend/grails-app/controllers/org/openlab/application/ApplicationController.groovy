package org.openlab.application

/**
 * Controller to manage applications, e.g. activate or deactivate them.
 * @author markus.list
 *
 */
class ApplicationController {

	def applicationHandlerService
	
	def index = {
			redirect(action: list)
	}
	
	def list = {
		//TODO	
	}
}
