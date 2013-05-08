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
