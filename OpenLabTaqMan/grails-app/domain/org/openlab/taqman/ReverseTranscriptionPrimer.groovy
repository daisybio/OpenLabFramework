package org.openlab.taqman

class ReverseTranscriptionPrimer {

    String name

    static constraints = {
    }

    static mapping = {
        table 'taqManReverseTranscriptionPrimer'
    }

    String toString() {
        name
    }
}
