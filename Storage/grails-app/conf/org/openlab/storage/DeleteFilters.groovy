package org.openlab.storage

import org.openlab.main.DataObject
import org.openlab.main.SubDataObject

class DeleteFilters {

    def filters = {
        storageCheckDelete(controller:'*', action:'delete') {
            before = {

                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                println clazz
                if(clazz.superclass == org.openlab.main.DataObject.class || clazz.superclass == org.openlab.main.SubDataObject.class){
                    def dataObj
                    def subDataObj
                    def storageElt

                    if(params.id) dataObj = DataObject.get(params.id)
                    if(params.id) subDataObj = SubDataObject.get(params.id)

                    if(dataObj) storageElt = StorageElement.findByDataObj(dataObj)
                    else if(subDataObj) storageElt = StorageElement.findBySubDataObj(subDataObj)

                    if(storageElt){
                        render "This object instance cannot be deleted as long as it is in storage!"
                        //redirect(controller: params.controller, action: 'show', params: params)

                        return false
                    }
                }
            }
        }
    }
}
