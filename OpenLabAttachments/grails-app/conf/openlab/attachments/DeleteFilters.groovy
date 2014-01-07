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
package openlab.attachments

import org.openlab.main.DataObject

class DeleteFilters {

    def filters = {
        attachmentCheckDelete(controller:'*', action:'delete') {
            before = {
                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                if(clazz.superclass == org.openlab.main.DataObject.class){

                    def dataObj = DataObject.get(params.id)
                    DataObjectAttachment.withCriteria{
                        dataObjects{
                            eq("id", Long.valueOf(params.id))
                        }
                    }.each{
                        if(it.dataObjects.size() > 1)
                        {
                            it.removeFromDataObjects(dataObj).save(flush: true)
                        }
                        else
                        {
                            it.delete(flush:true)
                        }
                    }

                    log.debug "cleared dataobject ${dataObj} from attachments before delete"
                }
            }
        }
    }
}
