/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.openlab.taglib

class JqueryDatePickerTagLib {

    def jqDatePicker = {attrs, body ->
        def out = out
        def name = attrs.name    //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        def minDate = attrs.minDate
        def showDay = attrs.showDay

        //Create date text field and supporting hidden text fields need by grails
        out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" value=\"${attrs.value?.getDateString()?:''}\" />"
        out.println "<input type=\"hidden\" name=\"${name}_day\" value=\"${attrs.value?.getDate()?:''}\" id=\"${id}_day\" />"
        out.println "<input type=\"hidden\" name=\"${name}_month\" value=\"${(attrs.value?.getMonth()?:0) + 1}\" id=\"${id}_month\" />"
        out.println "<input type=\"hidden\" name=\"${name}_year\" value=\"${(attrs.value?.getYear()?:0) + 1900}\" id=\"${id}_year\" />"

        //Code to parse selected date into hidden fields required by grails
        out.println "<script type=\"text/javascript\"> "
        out.println "var \$j = jQuery.noConflict();"
        //out.println "jQuery(document).ready(function(){"
        out.println "\$j(\"#${name}\").datepicker({"
        out.println "onClose: function(dateText, inst) {"
        out.println "\$j(\"#${name}_month\").attr(\"value\",new Date(dateText).getMonth() +1);"
        out.println "\$j(\"#${name}_day\").attr(\"value\",new Date(dateText).getDate());"
        out.println "\$j(\"#${name}_year\").attr(\"value\",new Date(dateText).getFullYear());"
        out.println "}"

        //If you want to customize using the jQuery UI events add an if block an attribute as follows
        if(minDate != null){
            out.println ","
            out.println "minDate: ${minDate}"
        }

        if(showDay != null){
            out.println ","
            out.println "beforeShowDay: function(date){"
            out.println	"var day = date.getDay();"
            out.println	"return [day == ${showDay},\"\"];"
            out.println "}"
        }

        out.println "});"
        //out.println "})"
        out.println "</script>"

    }
}