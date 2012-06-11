package org.openlab.taqman

class TaqManSetController {

    static scaffold = true

    def showDetails = {

        println params
        def taqManSetInstance = TaqManSet.get(params.id)

        [taqManSetInstance: taqManSetInstance, taqManSetResults: taqManSetInstance?.taqManResults]
    }
}
