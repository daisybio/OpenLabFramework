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
package org.openlab.dataimport.processor

import org.openlab.main.*;
import org.openlab.genetracker.*;
import org.openlab.genetracker.vector.*;
import org.openlab.storage.*;
import org.joda.time.*;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.openlab.dataimport.processor.*;
import org.openlab.dataimport.*;
import org.openlab.dataimport.creator.*;
import org.openlab.main.DataObject;
import groovy.transform.InheritConstructors

@InheritConstructors
class CellLineDataProcessor extends GeneTrackerImportClass{

	/**
	* Migrate Entry clones (CellLineData)
	* @return
	*/
   def createCellLineData()
   {
	   log.info "creating CellLineData..."
	   
	   def vector = Vector.findByLabel("pDONR201") //assumption
	   def pcrMap = getPcrAmplificationsMap()
	   def geneList = getGenes()
	   def cellLine = CellLine.findByLabel("A375")
	   
	   def acceptor = Acceptor.findByLabel("TOPO")
	   
	   importer.getEntryClones().each{ entryClone ->
		   //set user data and dates
		   entryClone = new UserDateProcessor(importer).setDateAndUserParams(entryClone)
		   
		   //find Recombinant or create when none found
		   def geneId = pcrMap."${entryClone.pcrId}"
		   def newId = geneList.find{it.oldId == geneId}?.newId
		   
		   //not valid if no id found
		   if(newId == null) return;
		   
		   def gene = Gene.get(newId)
		   def recombinant = Recombinant.findAllByVector(vector).find{it.genes.contains(gene)}
		   
		   if(recombinant == null)
		   {
			   recombinant = Recombinant.findAllByVector(Vector.findByLabel("pDONR221")).find{it.genes.contains(gene)}
		   }
		   
		   if(recombinant == null)
		   {
			   recombinant = new Recombinant(notes:'', creator: gene.creator, genes: [gene], vector: vector).save()
			   if(recombinant.hasErrors())
			   {
				   recombinant.errors.each{
					   log.error it.toString()
				   }
			   }
		   }
			   
		   entryClone.firstRecombinant = recombinant
		   
		   //cellLine A375 - lack of better knowledge
		   entryClone.cellLine = cellLine
		   
		   //acceptor TOPO - lack of better knowledge
		   entryClone.acceptor = acceptor
		   
		   //set notes from label + notes
		   entryClone.notes =  "" + entryClone.label + " / " + entryClone.notes
		   
		   entryClone.creator = gene.creator
		   
		   //save instance
		   def cellLineData = new CellLineData(entryClone).save()
		   if(cellLineData.hasErrors())
		   {
			   cellLineData.errors.each{
				   log.error it.toString()
			   }
		   }
		   else
		   {
			   entryClone.newId = cellLineData.id
			   freezeDataObject(cellLineData, entryClone.locationEntryClone)
		   }
	   }
   }
	
}
