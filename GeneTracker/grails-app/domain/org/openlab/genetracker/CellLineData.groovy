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

import org.openlab.genetracker.vector.Acceptor
import org.openlab.main.DataObject
import org.openlab.main.Project

class CellLineData extends DataObject {

    static constraints = {
        cellLine()
        acceptor(nullable: true)
        firstRecombinant(nullable: true)
        secondRecombinant(nullable: true)
        cultureMedia(nullable: true)
        mediumAdditives(nullable: true)
        plasmidNumber(nullable: true)
        colonyNumber(nullable: true)
        notes(nullable: true)
    }

    static mappedBy = [firstRecombinant: Recombinant, secondRecombinant: Recombinant]

    //make dataobjects target for searchable plugin
    static searchable = {
        boost 5.0
        mapping {
            firstRecombinant component: true
            secondRecombinant component: true
            cultureMedia component:true
            cellLine component: true
            acceptor component: true
            mediumAdditives: component: true
            //needed for suggestions in searchable
            spellCheck "include"
        }
    }


    static mapping = {
        table 'gtCellLineData'
        cache true
    }

    CellLine cellLine
    Acceptor acceptor
    Recombinant firstRecombinant
    Recombinant secondRecombinant
    CultureMedia cultureMedia
    String plasmidNumber
    String colonyNumber
    String notes

    static hasMany = [mediumAdditives: MediumAdditive, projects: Project]

    String toString() {
        def result
        if (secondRecombinant != null)
            result = "${cellLine} - ${acceptor} - ${firstRecombinant} - ${secondRecombinant}"
        else if (firstRecombinant != null)
            result = "${cellLine} - ${acceptor} - ${firstRecombinant}"
        else result = "${cellLine}"

        result += "${cultureMedia?' - '+cultureMedia:''}${colonyNumber?' - '+colonyNumber:''}"
        return result
    }

    String toBarcode() {
        if (secondRecombinant != null)
            "${cultureMedia ?: ''} - ${cellLine} - ${acceptor}~${firstRecombinant}~${secondRecombinant} - ${colonyNumber?:''}"
        else if (firstRecombinant != null)
            "${cultureMedia ?: ''} - ${cellLine} - ${acceptor}~${firstRecombinant} - ${colonyNumber?:''}"
        else "${cultureMedia ?: ''}~${cellLine}~${colonyNumber?:''}"
    }

    static String type = "cellLineData"
    static String typeLabel = "CellLineData"
}
