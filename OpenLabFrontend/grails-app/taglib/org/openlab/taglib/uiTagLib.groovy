package org.openlab.taglib

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 21-03-12
 * Time: 10:51
 */
class uiTagLib {
    
    def ampel = { p ->

        def colour

        if (p.bool == true) colour =  "#00cc00"
        else colour = "#cc0000"

        out << "<div style='width: 30px; height: 30px; background-color: ${colour};'></div>"
        //out << g.formatBoolean(boolean: bool as boolean , true: 'OK!', false:'NOT OK!')
    }
}
