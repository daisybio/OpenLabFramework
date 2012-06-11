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
