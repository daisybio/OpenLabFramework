package org.openlab.storage;

public class StorageElementController {

	def scaffold = StorageElement
	
	def addAttachment = {
		[attachToId:params.attachToId]
	}
}
