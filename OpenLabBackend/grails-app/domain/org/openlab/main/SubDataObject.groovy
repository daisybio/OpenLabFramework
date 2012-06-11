package org.openlab.main;

/**
 * DataObject is to be extended by all domain classes that are in some kind data that is not 
 * more or less static (contrary to master data).
 * @author markus.list
 *
 */
class SubDataObject extends MainObject {

    static String type = "subDataObject"
    static String typeLabel = "subDataObject"

    //static searchable = true

    static mapping = {
        //without this, all DataObjects would be stored within one single huge datatable.
        tablePerHierarchy false
        //alter the default relation name
        table 'olfSubDataObject'
    }
}	
