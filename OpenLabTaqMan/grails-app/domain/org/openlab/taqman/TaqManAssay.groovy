package org.openlab.taqman

class TaqManAssay implements Serializable{

    String name

    static constraints = {
        name unique: true
    }

    String toString()
    {
        name
    }
}
