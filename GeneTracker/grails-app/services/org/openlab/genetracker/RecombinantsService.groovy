package org.openlab.genetracker

class RecombinantsService {

    static transactional = true

	def recombinantsWithGene = { id ->
		def geneAsFirstRecombinant = CellLineData.withCriteria {
			firstRecombinant {
				genes {
					eq("id", Long.valueOf(id))
				}
			}
		}
		def geneAsSecondRecombinant = CellLineData.withCriteria {
			secondRecombinant {
				genes {
					eq("id", Long.valueOf(id))
				}
			}
		}

		geneAsFirstRecombinant.addAll(geneAsSecondRecombinant);
		Set cellLineData = geneAsFirstRecombinant
		return cellLineData
	}
}
