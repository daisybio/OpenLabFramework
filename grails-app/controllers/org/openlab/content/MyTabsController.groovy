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
package org.openlab.content

import org.openlab.module.Module

class MyTabsController {

    def grailsApplication

    def renderTab(){

        Module module = grailsApplication.getArtefact("Module", params.module).referenceInstance

        def model = module.getModelForDomainClass(params.domainClass, params.id)

        render(template: "/tabs/"+params.template, plugin: params.plugin, model: model)
    }

    def renderMobileTab(){

        Module module = grailsApplication.getArtefact("Module", params.module).referenceInstance

        def templateModel = module.getModelForDomainClass(params.domainClass, params.id)
        def model = ['templateName': "/tabs/" + params.template, templatePlugin: params.plugin,
        lastController: params.domainClass, lastId: params.id, templateModel: templateModel]

        render(view: "/tabs/mobile_tab", model: model)
    }
}
