package org.openlab.genetracker

import org.openlab.data.DataTableControllerTemplate;
import org.openlab.genetracker.vector.*;
import org.openlab.genetracker.*;
import org.openlab.data.*
import org.springframework.dao.DataIntegrityViolationException;

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

    def delete() {
        def recombinantInstance = Recombinant.get(params.id)
        if (!recombinantInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recombinant.label', default: 'Recombinant'), params.id])
            redirect(action: "show", params: [bodyOnly: params.bodyOnly?:false])
            return
        }

        else if(CellLineData.findByFirstRecombinant(recombinantInstance) || CellLineData.findBySecondRecombinant(recombinantInstance))
        {
            flash.message = "Recombinant cannot be deleted as long as it is part of a CellLineData object."
            redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
            return
        }

        try {
            recombinantInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recombinant.label', default: 'Recombinant'), params.id])
            redirect(action: "list", params: [bodyOnly: params.bodyOnly?:false])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recombinant.label', default: 'Recombinant'), params.id])
            redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
        }
    }
}
