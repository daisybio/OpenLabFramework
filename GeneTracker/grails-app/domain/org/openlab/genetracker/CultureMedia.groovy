package org.openlab.genetracker

import org.openlab.main.MasterDataObject

class CultureMedia extends MasterDataObject {

    static mapping = {
        table 'gtCultureMedia'
        cache true
    }

    String toString() {
        label
    }

    static searchable = true
}
