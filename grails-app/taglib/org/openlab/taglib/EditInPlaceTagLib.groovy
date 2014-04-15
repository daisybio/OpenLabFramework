/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
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

/**
 * TagLib to enable EditInPlace functionality utilizing scriptaculous JS library.
 * @author markus.list
 *
 */
class EditInPlaceTagLib {

	/**
	 * This tag adds in-line editing capabilities to a gsp page. It encapsulates the corresponding scriptaculous function call.
	 * Parameters are: url params including controller, action and id, #rows, #cols, paramName.
	 */
	def editInPlace = { attrs, body ->
	
		def rows = attrs.rows ? attrs.rows : 0;
		def cols = attrs.cols ? attrs.cols : 0;
		def id = attrs.remove('id')
		out << "<span id='${id}'>"
		out << body().toString().trim()
		out << "</span>"
		out << "<script type='text/javascript'>"
		out << "new Ajax.InPlaceEditor('${id}', '"
		out << createLink(attrs)
		out << "',{"
		out << "ajaxOptions: {method: 'post', onSuccess: function(){olfEvHandler.propertyValueChangedEvent.fire('${attrs.paramName}');}},"
		if(rows)
		out << "rows:${rows},"
		if(cols)
		out << "cols:${cols},"
		
		if(attrs.paramName) {
			out << """callback: function(form, value) {
				return '${attrs.paramName}=' + escape(value) }"""
		}
		out << "});"
		out << "</script>"
	}

	def editCollectionInPlace = { attrs, body ->
		def domainClass = grailsApplication.getDomainClass(attrs.referencedClassName)
		
		def collection = domainClass.newInstance().list()
		
		if(attrs.restriction)
		{
			if(attrs.restriction.subtype)
				collection = collection.findAll{it."${attrs.restriction.type}"."${attrs.restriction.subtype}" == attrs.restriction.value}
			else
				collection = collection.findAll{it."${attrs.restriction.type}" == attrs.restriction.value} 
		}
				
		collection = collection.collect{ ["'${it.id}'", "'${it.toString()}'"] }

		def id = domainClass.newInstance().getClass().getName()+"_"+attrs.paramName
		out << "<span id='${id}'>"
		out << body()
		out << "</span>"
		
		out << "<script type='text/javascript'>"
		out << "new Ajax.InPlaceCollectionEditor('${id}', '"
		out << createLink(attrs)
		out << "',{"
		out << "collection: ${collection}, ajaxOptions: {method: 'post', onSuccess: function(){olfEvHandler.propertyValueChangedEvent.fire('${attrs.paramName}');}},"
		if (attrs.paramName) {
			out << """callback: function(form, value) {
				return '${attrs.paramName}=' + escape(value) }"""
		}
		out << "});"
		out << "</script>"
	}									
}
