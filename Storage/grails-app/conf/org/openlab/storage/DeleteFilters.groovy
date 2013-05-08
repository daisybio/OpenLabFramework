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
package org.openlab.storage

import org.openlab.main.DataObject
import org.openlab.main.SubDataObject

class DeleteFilters {

    def filters = {
        storageCheckDelete(controller:'*', action:'delete') {
            before = {

                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                println clazz
                if(clazz.superclass == org.openlab.main.DataObject.class || clazz.superclass == org.openlab.main.SubDataObject.class){
                    def dataObj
                    def subDataObj
                    def storageElt

                    if(params.id) dataObj = DataObject.get(params.id)
                    if(params.id) subDataObj = SubDataObject.get(params.id)

                    if(dataObj) storageElt = StorageElement.findByDataObj(dataObj)
                    else if(subDataObj) storageElt = StorageElement.findBySubDataObj(subDataObj)

                    if(storageElt){
                        render "This object instance cannot be deleted as long as it is in storage!"
                        //redirect(controller: params.controller, action: 'show', params: params)

                        return false
                    }
                }
            }
        }
    }
}
