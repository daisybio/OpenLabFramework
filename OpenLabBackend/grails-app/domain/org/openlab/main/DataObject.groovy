package org.openlab.main

/**
 * DataObject is to be extended by all domain classes that are in some kind data that is not 
 * more or less static (contrary to master data).
 * @author markus.list
 *
 */
class DataObject extends MainObject {

    static String type = "dataObject"
    static String typeLabel = "dataObject"

    static belongsTo = Project

    static hasMany = [projects: Project]

    static mapping = {
        //without this, all DataObjects would be stored within one single huge datatable.
        tablePerHierarchy false
        //alter the default relation name
        table 'olfDataObject'
    }
}
