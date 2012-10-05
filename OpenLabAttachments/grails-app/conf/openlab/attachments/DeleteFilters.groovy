package openlab.attachments

import org.openlab.main.DataObject

class DeleteFilters {

    def filters = {
        attachmentCheckDelete(controller:'*', action:'delete') {
            before = {
                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                if(clazz.superclass == org.openlab.main.DataObject.class){

                    def dataObj = DataObject.get(params.id)
                    DataObjectAttachment.withCriteria{
                        dataObjects{
                            eq("id", Long.valueOf(params.id))
                        }
                    }.each{
                        if(it.dataObjects.size() > 1)
                        {
                            it.removeFromDataObjects(dataObj).save(flush: true)
                        }
                        else
                        {
                            it.delete(flush:true)
                        }
                    }

                    log.debug "cleared dataobject ${dataObj} from attachments before delete"
                }
            }
        }
    }
}
