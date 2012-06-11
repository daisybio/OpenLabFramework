package org.openlab.main

class Project extends MainObject {

    static constraints = {
        name(unique: true, blank: false)
        description()
        object(nullable: true)
    }

    /*//make dataobjects target for searchable plugin
     static searchable = {
         mapping {
             object component : true
         }
     }   */

    static belongsTo = [laboratory: Laboratory]
    static hasMany = [object: DataObject]
    static mapping =
        {
            table 'olfProject'
        }

    String name;
    String description;

    String toString() {
        name
    }
}
