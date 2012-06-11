package org.openlab.taglib;

import org.openlab.main.*;

public class ACLTagLib {

	def aclUtilService
	
	def includeAccessListForObject = { attrs->
		def mainObject = MainObject.get(params.id)
		
		/*if(mainObject instanceof DataObject)
		{
			aclUtilService.readAcl(mainObject)
		}*/
		return "There are currently no access restrictions."
	}
}
