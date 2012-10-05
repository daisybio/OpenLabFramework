package org.openlab.storage

import org.openlab.main.DataObject

class DeleteFilters {

    def filters = {
        storageCheckDelete(controller:'*', action:'delete') {
            before = {

                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                if(clazz.superclass == org.openlab.main.DataObject.class){
                    def dataObj
                    def storageElt

                    if(params.id) dataObj = DataObject.get(params.id)
                    if(dataObj) storageElt = StorageElement.findByDataObj(dataObj)

                    if(storageElt){
                        flash.message = "This object instance cannot be deleted as long as it is in storage!"
                        redirect(controller: params.controller, action: 'show', params: params)
                        return false
                    }
                }
            }
        }
    }
}
