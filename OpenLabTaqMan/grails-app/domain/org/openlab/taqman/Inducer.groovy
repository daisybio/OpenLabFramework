package org.openlab.taqman

class Inducer {

    String name
    int concentration

    static constraints = {
        name unique: 'concentration'
        concentration nullable: false
    }
    
    String toString()
    {
        "${name} ${concentration}"
    }
}
