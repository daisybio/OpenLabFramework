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
package org.openlab.barcode

import org.openlab.main.DataObject
import org.openlab.genetracker.Passage

class DeleteFilters {

    def filters = {

        barcodeCheckDelete(controller:'*', action:'delete') {
            before = {

                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                if(clazz.superclass == org.openlab.main.DataObject.class){

                    def dataObj = DataObject.get(params.id)
                    Barcode.findAllByDataObject(dataObj).each{
                        it.delete(flush:true)
                    }

                    log.debug "cleared dataobject ${dataObj} from barcode labels before delete"
                }
            }
        }

        barcodeSubCheckDelete(controller:'passage', action:'delete') {
            before = {

                def dataObj = Passage.get(params.id)
                Barcode.findAllBySubDataObject(dataObj).each{
                    it.delete(flush: true)
                }

                    log.debug "cleared passage ${dataObj} from barcode labels before delete"
            }
        }
    }
}
