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
        else "${cellLine}"

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
