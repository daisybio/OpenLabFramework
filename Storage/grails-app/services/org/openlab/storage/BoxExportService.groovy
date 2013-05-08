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
