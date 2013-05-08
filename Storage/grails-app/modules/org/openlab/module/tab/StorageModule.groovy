/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
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
