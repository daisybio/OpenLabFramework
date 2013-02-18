package org.openlab.settings

class UserSetting {

    String key
    String value

    static constraints = {
    }

    static mapping= {
        //datasource 'settings'
        id name:  'key' , generator: 'assigned'
        key column: 'skey'
        value column: 'svalue'
    }
}
