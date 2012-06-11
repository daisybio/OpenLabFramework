package org.openlab.storage

class CoordinatesTagLib {

	def boxCreationService
	
	def axisLabel = { attrs->
		out << boxCreationService.getAxisLabel(attrs.num, attrs.axis)
	}
	
}
