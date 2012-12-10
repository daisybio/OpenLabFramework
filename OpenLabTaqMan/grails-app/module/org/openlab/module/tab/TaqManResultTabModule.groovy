package org.openlab.module.tab

import org.openlab.module.*;
import org.openlab.main.*
import org.openlab.taqman.TaqManResult;

class TaqManResultTabModule implements Module{

    def getPluginName() {
        "open-lab-taq-man"
    }

    def getTemplateForDomainClass(def domainClass)
    {
        if(domainClass == "taqManResult") return "status"

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
            [taqManResultInstance: TaqManResult.get(id)]
        }
    }
}
