package org.openlab.genetracker

class AntibioticsWithConcentration {

    Antibiotics antibiotics
    String concentration
    CellLineData cellLineData

    static belongsTo = [cellLineData: CellLineData]

    static mapping = {
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
