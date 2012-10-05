package org.openlab.main

class ProjectFilters {

    def filters = {
        projectCheckDelete(controller:'*', action:'delete') {
            before = {

                Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == params.controller.toString().capitalize() }.clazz

                if(clazz.superclass == org.openlab.main.DataObject.class){

                    def dataObj = DataObject.get(params.id)
                    Project.withCriteria{
                      object{
                          eq("id", Long.valueOf(params.id))
                      }
                    }.each{
                        it.removeFromObject(dataObj)
                        it.save(flush: true)
                    }

                    log.debug "cleared dataobject ${dataObj} from projects before delete"
                }
            }
        }
    }
}
