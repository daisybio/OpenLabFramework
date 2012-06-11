package org.openlab.storage;

class StorageTypeController {

	def scaffold = StorageType
	
	def editElements =	{
			render(plugin: "storage", template:"/layouts/storageTypeEditElements")
	}
}
