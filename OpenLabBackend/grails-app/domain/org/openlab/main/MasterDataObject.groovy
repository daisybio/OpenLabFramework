package org.openlab.main

/**
 * Is to be extended by all domain classes that represent almost static data
 * that is input when the application is configured for the first time and
 * afterwards barely touched (also called master data).
 * @author markus.list
 *
 */
class MasterDataObject {

    static constraints = {
    }

    String label

    String toString() {
        label
    }

    static mapping = {
        sort "label"
        tablePerHierarchy false
        table 'olfMasterDataObject'
    }

    /*//make dataobjects target for searchable plugin
     static searchable = {
         mapping {
             //needed for suggestions in searchable
             spellCheck "include"
         }
     } */
}
