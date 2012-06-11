package org.openlab.error

/**
 * Created by spring security core to redirect in case of an error to 
 * corresponding GSPs.
 * @author markus.list
 *
 */
class ErrorController 
{

	def error403 = { render view: "error403"}

	def error500 = { render view: "error500" } 
	
	def error404 = { render view: "error404" }
}
