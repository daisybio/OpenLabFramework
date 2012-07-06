package org.openlab.taqman

class PurificationMethod {

    String name

    static constraints = {
    }

    static mapping = {
        table 'taqManPurificationMethod'
    }

    String toString()
    {
        name
    }
}
