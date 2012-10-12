package org.openlab.module.tab

import org.openlab.genetracker.*
import org.openlab.module.Module;

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 10-10-12
 * Time: 12:51
 */
class PrimerModule implements Module {
    def getPluginName() {
        "gene-tracker"
    }

    def getTemplateForDomainClass(def domainClass)
    {
        if(domainClass.startsWith("gene")) return "primerTab"

        else return null
    }

    def isInterestedIn(def domainClass, def type)
    {
        if((type == "tab") && domainClass.startsWith("gene")) return true
        return false
    }

    def getModelForDomainClass(def domainClass, def id)
    {
        if(domainClass.startsWith("gene"))
        {
            def gene = Gene.get(id)

            return [gene: gene, primers: Primer.findAllByGene(gene)]
        }
    }
}
