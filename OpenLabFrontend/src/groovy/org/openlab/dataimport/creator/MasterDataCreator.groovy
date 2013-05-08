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

@InheritConstructors
class MasterDataCreator extends GeneTrackerImportClass{
	
	/**
	* Migrate CellLines
	* @return
    */
   def createCellLines()
   {
	   log.info "create CellLines"
	   
	   def cultureMedia = CultureMedia.findByLabel("F12K")
	   
	   importer.getCellLineBasal().each{
		   it.cultureMedia = cultureMedia
		   def cellLine = new CellLine(it).save()
		   
		   if(cellLine.hasErrors())
		   {
			   cellLine.errors.each{
				   log.error(it.toString())
			   }
		   }
	   }
   }

	def createBasicCellLineData()
	{
		log.info "creating master data for CellLines..."
		
		def origin1 = new Origin(label: "cDNA", description: "cDNA")
		origin1.save()
		
		def origin2 = new Origin(label: "Cell-Line", description: "Cell-Line")
		origin2.save()
		
		def origin3 = new Origin(label: "Library Clone", description : "Library Clone")
		origin3.save()
		
		def ampicillin = new Antibiotics(label: 'Ampicillin')
		ampicillin.save()
		
		def penicillin = new Antibiotics(label: 'Penicillin')
		penicillin.save()
		
		def vancomycin = new Antibiotics(label: 'Vancomycin')
		vancomycin.save()
		
		def kanamycin = new Antibiotics(label: 'Kanamycin')
		kanamycin.save()
		
		def dmem = new CultureMedia(label: 'DMEM')
		dmem.save()
		
		def f12k = new CultureMedia(label: 'F12K')
		f12k.save()
		
		def rpmi = new CultureMedia(label: 'RPMI')
		rpmi.save()
		
		def hydroc = new MediumAdditive(label: 'Hydrocortisone', description: '')
		hydroc.save()
		
		def insulin = new MediumAdditive(label: 'Insulin', description: '')
		insulin.save()
	
		def fks = new MediumAdditive(label: '5% FKS', description: '')
		fks.save()
		
		def fks10 = new MediumAdditive(label: '10% FKS', description: '')
		fks10.save()
		
		def ags = new CellLine(notes: '', label: 'AGS', antibiotics: [penicillin, vancomycin], cultureMedia: f12k, goodies: [hydroc, fks])
		ags.save()
		
		def a375 = new CellLine(notes: '', label: 'A375', antibiotics: [], cultureMedia: rpmi, goodies: [fks10])
		a375.save()
		
		def skmel = new CellLine(notes: '', label: 'SK-MEL-28', antibiotics: [], cultureMedia: dmem, goodies: [fks10])
		skmel.save()
	}
	
	def createVectors()
	{
		log.info "creating Vectors..."
		
		def vector1 = new Acceptor(label: "pPAR3", description: "", type: "Acceptor")
		vector1.save()
		
		def vector2 = new IntegrationFirst(label: "pDONR221", description: "", type: "Cloning Vector")
		vector2.save()
		
		def vector3 = new IntegrationFirst(label: "pDONR201", description: "", type: "Cloning Vector")
		vector3.save()
		
		def vector4 = new IntegrationFirst(label: "pDONR223", description: "", type: "Cloning Vector")
		vector4.save()
		
		def vector5 = new IntegrationFirst(label: "duplicates", description: "import combines with gene with that vector if combination already existed.", type: "Cloning Vector")
		vector5.save()
		
		def vector6 = new CloningVector(label: "pDEST-1", description: "", type: "Integration (First)")
		vector6.save()
		
		def vector7 = new CloningVector(label: "pDEST-2", description: "", type: "Integration (First)")
		vector7.save()
	}
	
	static def commonOrganisms = [
		"Arabidopsis thaliana" : "Arabidopsis",
		"Hepatitis C virus" : "Hep. C",
		"Pneumocystis carinii": "P. carinii",
		"Bos taurus": "Bovine",
		"Homo sapiens": "Human",
		"Rattus norvegicus": "Rat",
		"Caenorhabditis elegans": "C. elegans",
		"Magnaporthe grisea": "M. grisea",
		"Saccharomyces cerevisiae": "Yeast",
		"Chlamydomonas reinhardtii": "C. reinhardtii",
		"Mus musculus": "Mouse",
		"Schizosaccharomyces pombe": "S. pombe",
		"Danio rerio" : "Zebrafish",
		"Mycoplasma pneumoniae" : "M. pneumoniae",
		"Takifugu rubripes" : "Pufferfish",
		"Dictyostelium discoideum" : "D. discoideum",
		"Neurospora crassa" : "N. crassa",
		"Xenopus laevis" : "X. laevis",
		"Drosophila melanogaster" : "Drosophila",
		"Oryza sativa" : "Rice",
		"Zea mays" : "Maize",
		"Escherichia coli" : "E. coli",
		"Plasmodium falciparum" : "P. falciparum"
	]
	
	def createCommonOrganisms()
	{
		commonOrganisms.keySet().each {
			def organism = new Organism(name: it, label: commonOrganisms[it], description: "")
			organism.save(flush: true)
		}
	}
	
	def createLaboratories()
	{
		new Laboratory(name: "Odense", description: "NanoCAN").save()
		new Laboratory(name: "Heidelberg", description: "DKFZ").save()
	}
}
