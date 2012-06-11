package org.openlab.taqman

import org.openlab.main.MainObject

class TaqManSet extends MainObject implements Serializable{

    String name
    static hasMany = [taqManResults : TaqManResult]
    
    static constraints = {
        name nullable: false
    }

    static mapping = {
        table 'taqManSet'
    }
    
    String toString()
    {
        name
    }
}
