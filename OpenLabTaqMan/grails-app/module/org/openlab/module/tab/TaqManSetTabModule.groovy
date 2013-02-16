package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.main.*
import org.openlab.taqman.TaqManResult
import org.openlab.taqman.TaqManSet;

class TaqManSetTabModule implements Module{

    def getPluginName() {
        "open-lab-taq-man"
    }

    def getTemplateForDomainClass(def domainClass)
    {
        if(domainClass == "taqManResult") return "addToSet"

        else return null
    }

    def isInterestedIn(def domainClass, def type)
    {
        if((type == "tab") && domainClass == "taqManResult") return true
        return false
    }

    def getModelForDomainClass(def domainClass, def id)
    {
        if(domainClass == "taqManResult")
        {
            def taqManSets =  TaqManSet.withCriteria{
            taqManResults
                    {
                        'in' ("id", taqManResultInstance?.id)
                    }
        }
            [taqManResultInstance: TaqManResult.get(id), taqManSets: taqManSets]
        }
    }
}
