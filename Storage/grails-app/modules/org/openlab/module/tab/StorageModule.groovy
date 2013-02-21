package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.module.*
import org.openlab.storage.StorageElement
import org.openlab.main.DataObject

public class StorageModule implements Module{

	def getPluginName() {
		return "storage";
	}
	
	def getTemplateForDomainClass(def domainClass)
	{
		if(domainClass.startsWith("recombinant")) return "storage";
        else if(domainClass.startsWith("cellLineData")) return "cellLineDataStorage"
	}

    def getMobileTemplateForDomainClass(def domainClass) {
        if(domainClass.startsWith("recombinant")) return "mobile_storage";
        else if(domainClass.startsWith("cellLineData")) return "mobile_cellLineDataStorage"
    }
	
	def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && (domainClass.startsWith("cellLineData") || domainClass.startsWith("recombinant"))) return true
		
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		def storageElts
            try{
                storageElts = StorageElement.findAllByDataObj(DataObject.get(Long.valueOf(id)))
            } catch(NumberFormatException e)
            {
                log.error "Could not parse id for storage module " + e.getMessage()
            }
        return [storageElts: storageElts]
	}

    def isMobile()
    {
        return true
    }
}
