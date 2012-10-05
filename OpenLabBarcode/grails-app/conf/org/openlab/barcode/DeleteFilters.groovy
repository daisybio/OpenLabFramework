package org.openlab.barcode

import org.openlab.main.DataObject
import org.openlab.genetracker.Passage

class DeleteFilters {

    def filters = {

        barcodeCheckDelete(controller:'*', action:'delete') {
            before = {

                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                if(clazz.superclass == org.openlab.main.DataObject.class){

                    def dataObj = DataObject.get(params.id)
                    Barcode.findAllByDataObject(dataObj).each{
                        it.delete(flush:true)
                    }

                    log.debug "cleared dataobject ${dataObj} from barcode labels before delete"
                }
            }
        }

        barcodeSubCheckDelete(controller:'passage', action:'delete') {
            before = {

                def dataObj = Passage.get(params.id)
                Barcode.findAllBySubDataObject(dataObj).each{
                    it.delete(flush: true)
                }

                    log.debug "cleared passage ${dataObj} from barcode labels before delete"
            }
        }
    }
}
