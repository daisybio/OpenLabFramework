package org.openlab.genetracker

import org.openlab.data.DataTableControllerTemplate;
import org.openlab.genetracker.vector.*;
import org.openlab.genetracker.*;
import org.openlab.data.*;

class RecombinantController extends DataTableControllerTemplate{

	def scaffold = Recombinant

    def addRecombinantInTab(){

        new Recombinant(vector: Vector.get(params.vector), notes: '', genes: [Gene.get(params.gene)]).save(flush:true, failOnError: true)

        def gene = Gene.get(params.gene)
        def gvectors = Recombinant.withCriteria{
            genes{
                eq("id", Long.valueOf(params.gene))
            }
        }
        def vectors = Vector.where{ type != "Acceptor"}.list(sort: "label")

        def newModel = [gene: gene, geneVectorList: gvectors, vectors: vectors]

        render(template: "/tabs/geneVectorTab", model: newModel)
    }
}
