package org.openlab.genetracker

class AntibioticsWithConcentration {

    Antibiotics antibiotics
    String concentration
    CellLineData cellLineData
    String notes

    static belongsTo = [cellLineData: CellLineData]

    static mapping = {
        notes nullable: true
        antibiotics sort: 'label'
        table 'gtAntibioticsWithC'
        cache true
    }

    static searchable = true/*{
		mapping {
			antibiotics component: true
			cellLineData reference: true
		}
	}                         */

    String toString() {
        antibiotics.toString() + " - " + concentration.toString()
    }
}
