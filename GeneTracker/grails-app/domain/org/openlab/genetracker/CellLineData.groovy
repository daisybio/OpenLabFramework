package org.openlab.genetracker

import org.openlab.genetracker.vector.Acceptor
import org.openlab.main.DataObject
import org.openlab.main.Project

class CellLineData extends DataObject {

    static constraints = {
        cellLine()
        acceptor()
        firstRecombinant(nullable: true)
        secondRecombinant(nullable: true)
        cultureMedia(nullable: true)
        antibiotics(nullable: true)
        mediumAdditives(nullable: true)
        plasmidNumber(nullable: true)
        colonyNumber(nullable: true)
        notes(nullable: true)
    }

    static mappedBy = [firstRecombinant: Recombinant, secondRecombinant: Recombinant]

    //make dataobjects target for searchable plugin
    static searchable = {
        mapping {
            boost 5.0
            firstRecombinant component: true
            secondRecombinant component: true
            cultureMedia component:true
            cellLine component: true
            acceptor component: true
            antibiotics: component: true
            mediumAdditives: component: true
            //needed for suggestions in searchable
            spellCheck "include"
        }
    }


    static mapping = {
        table 'gtCellLineData'
        cache true
        mediumAdditives cache: true
        antibiotics cache: true
        projects cache: true
    }

    CellLine cellLine
    Acceptor acceptor
    Recombinant firstRecombinant
    Recombinant secondRecombinant
    CultureMedia cultureMedia
    String plasmidNumber
    String colonyNumber
    String notes

    static hasMany = [mediumAdditives: MediumAdditive, antibiotics: AntibioticsWithConcentration, projects: Project]

    String toString() {
        if (secondRecombinant != null)
            "${cellLine} - ${acceptor} - ${firstRecombinant} - ${secondRecombinant} - ${cultureMedia ?: ''}"
        else if (firstRecombinant != null)
            "${cellLine} - ${acceptor} - ${firstRecombinant} - ${cultureMedia ?: ''}"
        else "${cellLine} - ${cultureMedia ?: ''}"
    }

    static String type = "cellLineData"
    static String typeLabel = "CellLineData"
}
