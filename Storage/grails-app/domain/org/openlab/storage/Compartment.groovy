package org.openlab.storage

class Compartment {

    String description
    StorageType storageType
    
    static hasMany = [boxes : Box]
    static belongsTo = StorageType
    
    String toString()
    {
    	"${storageType} - ${description}"
    }
}
