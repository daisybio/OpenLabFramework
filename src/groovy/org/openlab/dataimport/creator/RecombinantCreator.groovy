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
package org.openlab.dataimport.creator

import org.openlab.security.*;
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
import groovy.transform.InheritConstructors


class RecombinantCreator extends GeneTrackerImportClass{
	
	def storageProcessor
	def userDateProcessor
	
	public RecombinantCreator(def importer)
	{
		super(importer)
		
		this.userDateProcessor = new UserDateProcessor(importer)
		this.storageProcessor = new StorageProcessor(importer)
	}
	
	/**
	* Migrate Recombinants (Strains)
	* @return
	*/
   def createRecombinantsFromStrains()
   {
	   log.info "creating Recombinants from strains..."
	   
	   def genes = importer.getGenes()
	   
	   importer.getStrains().each{ strain ->
		   
		   strain.notes = "" + strain.label + " / " + strain.description
		   def newId = genes.find{it.oldId == strain.geneOldId}.newId
		   
		   if(!newId){
			   log.warn "no gene ID found for old id ${strain.geneOldId}."
		   }
		   
		   def gene = Gene.get(newId)
		   
		   if(!gene)
		   {
			   log.warn "no gene found for id ${newId}."
			   return;
		   }
		   
		   strain.genes = [gene]
		   strain.creator = gene.creator
		   
		   //strain."${'geneId'}" = gene.id
		   
		   def vector
		   
		   //try to find out which vector to use
		   if(strain.description && strain?.description?.toString()?.startsWith("pDONR201"))
		   {
			   vector = Vector.findByLabel("pDONR201")
		   }
			   
		   else vector = Vector.findByLabel("pDONR221")
		   
		   strain.vector = vector
		   //strain."${'vectorId'}" = vector.id
		  
		   strain.creator = gene.creator
		   
		   //create Recombinant
		   def recombinant = new Recombinant(strain).save(flush:true)
		   if(recombinant == null)
		   {
			   vector = Vector.findByLabel("duplicates")
			   strain.vector = vector
			   strain."${'vector.id'}" = vector.id
			   recombinant = new Recombinant(strain).save(flush:true)
		   }
   
		   if(recombinant.hasErrors())
		   {
			   recombinant.errors.each{
				   log.error it.toString()
			   }
		   }
		   else
		   {
			   	storageProcessor.freezeDataObjectWithManualStorage(recombinant, strain.storageLocation)
		   }
	   }
   }
   
   /**
	* Migrate Entry clones to Recombinants (pDEST)
	* @return
	*/
   def createRecombinantsFromEntryClones()
   {
	   log.info "creating Recombinants from entry clones..."
	   
	   def vector = Vector.findByLabel("pDEST-1") //assumption
	   
	   def pcrMap = importer.getPcrAmplificationsMap()
	   def geneList = importer.getGenes()
	   
	   importer.getEntryClones().each{ entryClone ->
		   //set user data and dates
		   entryClone = userDateProcessor.setDateAndUserParams(entryClone)
		   
		   //create Recombinant if does not already exist
		   def geneId = pcrMap."${entryClone.pcrId}"
		   def newId = geneList.find{it.oldId == geneId}?.newId
		   
		   //not valid if no id found
		   if(newId == null)
		   {
			   log.warn "Gene id not found. Could not process entry clone ${entryClone.oldId}."
			   return;
		   }
		   
		   def gene = Gene.get(newId)
		   
		   if(!gene)
		   {
				log.warn "Gene not found. Could not process entry clone ${entryClone.oldId}."
				return;
		   }
		   
		   //check if exists
		   def recombinant = Recombinant.findAllByVector(vector).find{it.genes.contains(gene)}
		   if (!recombinant)
		   {
			   recombinant = new Recombinant(notes:'', creator: gene.creator, genes: [gene], vector: vector).save()
			   
			   if(recombinant.hasErrors())
			   {
				   recombinant.errors.each{
					   log.error it.toString()
				   }
			   }
		   }
		   
		   storageProcessor.freezeDataObjectWithManualStorage(recombinant, entryClone.locationEntryClone)
	   }
   }
}
