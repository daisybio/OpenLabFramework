package org.openlab.storage

import de.andreasschmitt.export.builder.*

class StorageExportService {
	
	def export(def response)
	{
		def builder = new ExcelBuilder()
		
		builder {
			 workbook(outputStream: response.outputStream)
			 {
				 sheet(name: "Storage_Hierarchy")
				 {
					format(name: "format1"){
						font(name: "Arial", size: 12, bold: false, underline: "single", italic: true)
					}
					int x = 1;
					
					/* HEADER */
					
					cell(row: 0, column: 0, value: "StorageType")
					cell(row: 0, column: 1, value: "Compartment")
					cell(row: 0, column: 2, value: "Box")
					
					/* BODY */
					
					format(name: "format1"){
						font(name: "Arial", size: 10, bold: true, underline: "single", italic: true)
					}
						
					Freezer.list().each{freezer ->
						cell(row: x, column: 0, value: freezer.description)
						
						freezer.compartments.each{compartment ->
							cell(row: x, column: 1, value: compartment.description)
							
							compartment.boxes.each{box ->
								cell(row: x++, column: 2, value: box.description)
							}
							x++
						}
						x++
					}
				}
			 }
		}
		
		builder.write()
	}
}
