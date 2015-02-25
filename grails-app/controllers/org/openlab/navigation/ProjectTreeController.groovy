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
package org.openlab.navigation;

import org.openlab.main.Project
import grails.converters.JSON
import org.openlab.genetracker.Gene
import org.openlab.genetracker.Recombinant
import org.openlab.genetracker.CellLineData

/**
 * Controller to deliver XML data to the JS tree that is showing
 * the project structure.
 * @author markus.list
 *
 */
public class ProjectTreeController {

    def index = { renderTree }

    def renderTree(){
        [:]
    }

    def projectTreeAsJSON() {

        if(params.int("id") == 0)
        {
            def projects = Project.list(sort: "name")

            def projectsAsJSON = projects.collect{
                def state = ""
                if(it?.object?.size() > 0) state = "closed"
                [
                        "data" : it.name,
                        "attr" : [ "id" : it.id , "rel":"project", "nodeType" : "project"],
                        "state" : "${state}"
                ]
            }
            render projectsAsJSON as JSON
        }

        else if(params.nodeType == "project"){
            def genesAsJSON = Gene.withCriteria{ projects { eq('id', params.long("id")) }}.sort{it.name}.collect{ gene ->
                def state = ""
                if(Recombinant.withCriteria{ genes { eq('id', gene.id) }}) state = "closed"

                [
                        "data" : gene.name,
                        "attr" : [ "id" : gene.id , "rel":"gene", "nodeType" : "gene"],
                        "state": "${state}"
                ]
            }

            render genesAsJSON as JSON
        }

        else if(params.nodeType == "gene"){

            def geneVectorAsJSON = Recombinant.withCriteria{ genes { eq('id', params.long("id")) }}.sort{it.toString()}.collect{ recombinant ->
                def state = ""

                if(CellLineData.withCriteria{ firstRecombinant{ eq('id', recombinant.id) } } || CellLineData.withCriteria{ secondRecombinant{ eq('id', recombinant.id) } }) state = "closed"
                [
                        "data" : recombinant.toString(),
                        "attr" : [ "id" : recombinant.id , "rel":"gene", "nodeType" : "geneVector"],
                        "state": "${state}"
                ]
            }

            render geneVectorAsJSON as JSON
        }

        else if(params.nodeType == "geneVector"){
            def firstRecombinants = CellLineData.withCriteria{ firstRecombinant{ eq('id', params.long("id")) }}
            def secondRecombinants = CellLineData.withCriteria{ secondRecombinant{ eq('id', params.long("id")) }}
            def recombinants = firstRecombinants
            recombinants.addAll(secondRecombinants)

            def cellLineDataAsJSON = recombinants.sort{it.toString()}.collect{
                [
                        "data" : it.toString(),
                        "attr" : [ "id" : it.id , "rel":"gene", "nodeType" : "cellLineData"]
                ]
            }

            render cellLineDataAsJSON as JSON
        }
    }

}
