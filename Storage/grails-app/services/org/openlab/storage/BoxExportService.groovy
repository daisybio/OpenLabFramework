package org.openlab.storage

import de.andreasschmitt.export.builder.*

class BoxExportService {

	def boxCreationService
	
	def export(def id, def response)
	{
		def box = Box.get(id)
		def builder = new ExcelBuilder()
		
		builder {
			 workbook(outputStream: response.outputStream){
		
				 sheet(name: box.toString()){
						format(name: "format1"){
						 font(name: "Arial", size: 10, bold: true, underline: "single", italic: true)
						}
				 	/* Header */
						for(int x = 1; x < box.xdim; x++)
						{
							cell(row: x, column: 0, value: boxCreationService.getAxisLabel(x-1, "x"))
						}
						
						for(int y = 1; y <= box.ydim; y++)
						{
							cell(row: 0, column: y, value: boxCreationService.getAxisLabel(y-1, "y"))
						}
						
					/* Body */
						def cells = []
						def xdim = box.xdim
						def ydim = box.ydim
						def sortedElements = boxCreationService.getSortedBoxElements(box.id)
						
						sortedElements.each{element ->
							cells += cell(row: element.xcoord+1, column: element.ycoord+1, value: element.toString())
						}
				 }
			 }
		 }
		
		builder.write()
	}
}
