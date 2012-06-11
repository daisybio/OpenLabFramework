package org.openlab.taqman

import org.openlab.genetracker.CellLineData

class TaqManSample implements Serializable{

    String sampleName
    CellLineData cellLineData
    Inducer inducer
    String sampleType

    static belongsTo = [taqManResult: TaqManResult]

    static constraints = {
        sampleType inList: TaqManResultController.sampleTypes, nullable: false
        cellLineData nullable: true
        inducer nullable: true
    }

    String toString() {
        sampleName
    }
}
