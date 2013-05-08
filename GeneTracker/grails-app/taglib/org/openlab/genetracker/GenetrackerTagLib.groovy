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
package org.openlab.genetracker

class GenetrackerTagLib {

	def settingsService
	
	def ncbiSequenceViewer = { attrs ->
		def url = settingsService.getSetting(key: "genetracker.ncbi.sequenceviewer.url")
		
		def gene = Gene.get(attrs.geneId) 
		
		if(url && gene.accessionNumber && (gene.geneType == "Wildtype"))
		{
			//out << """<iframe width=100% height="400" src="${url}?id=${gene.accessionNumber}#"/>"""
			out << """<object data="${url}?id=${gene.accessionNumber}#" type="text/html" width="100%" height="500">
			</object>
			"""
		 
		}
		else if(gene.geneType != "Wildtype")
		{
			out << settingsService.getLabel(key: "sequenceviewer.nourl")?:"can only be displayed for wildtype NCBI genes"
		}
		else{
			out << settingsService.getLabel(key: "sequenceviewer.nourl")?:"no valid url"
		}
	}
	
}
