package org.openlab.taqman

class Inducer {

    String name
    int concentration

    static constraints = {
        name unique: 'concentration'
        concentration nullable: false
    }

    static mapping = {
        table 'taqManInducer'
    }
    
    String toString()
    {
        "${name} ${concentration}"
    }
}
