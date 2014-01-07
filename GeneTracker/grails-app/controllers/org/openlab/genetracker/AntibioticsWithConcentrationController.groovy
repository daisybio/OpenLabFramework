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
package org.openlab.genetracker

import org.springframework.dao.DataIntegrityViolationException
import org.openlab.module.tab.AntibioticsModule

class AntibioticsWithConcentrationController {

    def scaffold = true

    def addAntibioticsInTab(){

        new AntibioticsWithConcentration(params).save(flush:true, failOnError: true)

        def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))

        render(template: "/tabs/antibioticsTab", model: newModel)
    }

    def delete() {
        def antibioticsInstance = AntibioticsWithConcentration.get(params.long("id"))

        if (!antibioticsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'antibioticsWithConcentration.label', default: 'Antibiotics'), params.id])
            def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))
            render(template: "/tabs/antibioticsTab", model: newModel)
            return
        }

        try {
            antibioticsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'antibioticsWithConcentration.label', default: 'Antibiotics'), params.id])
            def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))
            render(template: "/tabs/antibioticsTab", model: newModel)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'antibioticsWithConcentration.label', default: 'Antibiotics'), params.id])
            def newModel = new AntibioticsModule().getModelForDomainClass("cellLineData", params.long("cellLineData.id"))
            render(template: "/tabs/antibioticsTab", model: newModel)
        }
    }
}
