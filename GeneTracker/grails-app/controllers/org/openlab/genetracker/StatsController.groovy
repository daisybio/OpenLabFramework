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

import org.openlab.main.Project
import grails.converters.JSON
import org.openlab.main.Laboratory
import org.openlab.security.User

class StatsController {

    def geneStats() {
        def nanocanLab = Laboratory.findByName("Odense")

        def genesAsJSON = [
            cols : [[id: "", label:"projects", pattern: "", type: "string"], [id: "", label: "number of genes", pattern: "", type: "number"]],
            rows : Project.findAllByLaboratory(nanocanLab).collect{ project ->
                [c: [[v: project.name, f: null], [v: Gene.withCriteria{ projects { eq('id', project.id) }}.size(), f: null]]]
            }
        ]

        render genesAsJSON as JSON
    }

    def recombinantStats() {
        def nanocanLab = Laboratory.findByName("Odense")

        def recombinantsAsJSON = [
                cols : [[id: "", label:"projects", pattern: "", type: "string"], [id: "", label: "number of recombinants", pattern: "", type: "number"]],
                rows : Project.findAllByLaboratory(nanocanLab).collect{ project ->
                    [c: [[v: project.name, f: null], [v: Recombinant.withCriteria{ projects { eq('id', project.id) }}.size(), f: null]]]
                }
        ]

        render recombinantsAsJSON as JSON
    }

    def cellLineDataStats() {
        def nanocanLab = Laboratory.findByName("Odense")

        def cellLineDataAsJSON = [
                cols : [[id: "", label:"projects", pattern: "", type: "string"], [id: "", label: "number of cell line recombinants:", pattern: "", type: "number"]],
                rows : Project.findAllByLaboratory(nanocanLab).collect{ project ->
                    [c: [[v: project.name, f: null], [v: CellLineData.withCriteria{ projects { eq('id', project.id) }}.size(), f: null]]]
                }
        ]

        render cellLineDataAsJSON as JSON
    }

    def genesByUserStats(){
        def genesByUserAsJSON = [
                cols : [[id: "", label:"user", pattern: "", type: "string"], [id: "", label: "genes by user:", pattern: "", type: "number"]],
                rows : User.list().collect{ user ->
                    [c: [[v: user.username, f: null], [v: Gene.withCriteria{ projects { eq('creator.id', user.id) }}.size(), f: null]]]
                }
        ]

        render genesByUserAsJSON as JSON
    }
}
