package org.openlab.dataimport

import org.grails.plugins.excelimport.ExcelImportUtils
import org.grails.plugins.excelimport.*
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

/**
 * A class that utilizes the excel-import plugin (which again utilizes Apache POI) 
 * to migrate existing genetracker data to the new open laboratory framework.
 * Relations of the old database have to be put into one excel file where
 * each relation corresponds to one excel sheet. These sheets are then parsed to
 * OLF objects in an order dictated by their dependencies. IDs from the old
 * genetracker application are exploited to assess relationships.   
 * @author markus.list
 *
 */
class GeneTrackerImporter extends AbstractExcelImporter
{	
	/*
	 * variables for already fetched data
	 */
	def userList
	def projectList
	def geneList
	def projectGeneL
	def projectGeneM
	def pcrAmplificationsL
	def pcrAmplificationsM
	def cellLineBasalL
	def entryClonesL
	def strainsL
	def freezersL
	def compartmentsL
	def storageLocationsL
	def numOfUnnamedGenes
	def sequencesL
	def sequencesM
	
	/*
	 * Create Logger
	 */
	static Log log = LogFactory.getLog(GeneTrackerImporter.class)
	
	/* configuration maps that tell us which column corresponds to which value */
	static Map CONFIG_USERS_COLUMN_MAP = [ sheet:'users', startRow: 2, columnMap: [ 'A': 'oldId', 'B':'lastName', 'C':'firstName', 'D':'username', 'E':'email' , 'G':'abbr'] ]
	static Map CONFIG_PROJECTS_COLUMN_MAP = [ sheet:'projects', startRow: 2, columnMap: [ 'A': 'oldId', 'B':'name', 'C':'insertUserOldId', 'D':'dateCreated', 'E':'editUserOldId' , 'F':'lastUpdate', 'G': 'description'] ]
	static Map CONFIG_GENES_COLUMN_MAP = [ sheet:'genes', startRow: 2, columnMap: [ 'A': 'oldId', 'B':'name', 'D':'accessionNumber', 'L':'length', 'P':'dateCreated', 'Q':'insertUserOldId' , 'R':'lastUpdate', 'S':'editUserOldId', 'T':'notes', 'V':'storageLocation'] ]
	static Map CONFIG_PROJECTGENES_COLUMN_MAP = [ sheet:'projectgenes', startRow: 2, columnMap: [ 'A':'projectId', 'B':'geneId'] ]
	static Map CONFIG_PCRAMPLIFICATIONS_COLUMN_MAP = [ sheet:'pcramplifications', startRow: 2, columnMap: [ 'A':'pcrId', 'B':'geneId'] ]
	static Map CONFIG_CELLLINEBASAL_COLUMN_MAP = [ sheet:'celllinebasal', startRow: 2, columnMap: [ 'A':'oldId', 'B':'label', 'C':'notes'] ]
	static Map CONFIG_ENTRYCLONES_COLUMN_MAP = [ sheet:'entryclones', startRow: 2, columnMap: [ 'A':'oldId', 'F':'pcrId', 'J':'insertUserOldId', 'K':'dateCreated', 'L':'editUserOldId', 'M':'lastUpdate', 'O':'locationEntryClone', 'P':'locationDna', 'S':'label', 'T':'notes'] ]
	static Map CONFIG_STRAINS_COLUMN_MAP = [ sheet:'strains', startRow: 2, columnMap: [ 'A':'oldId', 'B':'geneOldId', 'D':'storageLocation', 'E':'description', 'F':'label'] ]
	static Map CONFIG_FREEZERS_COLUMN_MAP = [ sheet:'freezers', startRow: 2, columnMap: [ 'A':'oldId', 'C':'temperature', 'D':'oldLocation', 'E':'description'] ]
	static Map CONFIG_COMPARTMENTS_COLUMN_MAP = [ sheet:'compartments', startRow: 2, columnMap: [ 'A':'oldId', 'B':'description', 'C':'freezerId'] ]
	static Map CONFIG_STORAGELOCATIONS_COLUMN_MAP = [ sheet:'storagelocations', startRow: 2, columnMap: [ 'A':'oldId', 'B':'freezerId', 'C':'compartmentId', 'D':'boxName', 'E':'oldX', 'F':'oldY', 'I':'insertUserOldId', 'J': 'dateCreated', 'K':'editUserOldId', 'L':'lastUpdate'] ]
	static Map CONFIG_SEQUENCES_COLUMN_MAP = [ sheet:'sequences', startRow: 2, columnMap: [ 'A':'oldId', 'B':'accessionNumber', 'C':'sequence'] ]
	/* constructor */
	public GeneTrackerImporter(fileName) 
	{
		super(fileName) 
	}
	
	/*
	 * Getters for all sheets
	 */
	List<Map> getFreezers()
	{
		if(!freezersL)
		{
			freezersL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_FREEZERS_COLUMN_MAP)
		}
		else freezersL
	}
	
	List<Map> getCompartments()
	{
		if(!compartmentsL)
		{
			compartmentsL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_COMPARTMENTS_COLUMN_MAP)
		}
		else compartmentsL
	}
	
	List<Map> getStorageLocations()
	{
		if(!storageLocationsL)
		{
			storageLocationsL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_STORAGELOCATIONS_COLUMN_MAP)
		}
		
		else storageLocationsL
	}
	
	List<Map> getStrains()
	{
		if(!strainsL)
			strainsL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_STRAINS_COLUMN_MAP)
		else strainsL
	}
	
	List<Map> getUsers() 
	{ 
		if(!userList)
			userList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_USERS_COLUMN_MAP) 
		else userList
	}
	
	List<Map> getProjects()
	{
		if(!projectList)
			projectList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_PROJECTS_COLUMN_MAP)
		else projectList
	}
	
	List<Map> getGenes()
	{
		if(!geneList)
			geneList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_GENES_COLUMN_MAP)
		else geneList
	}
	
	List<Map> getProjectGenes()
	{
		if(!projectGeneL)
			projectGeneL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_PROJECTGENES_COLUMN_MAP)
		else projectGeneL
	}
	
	List<Map> getCellLineBasal()
	{
		if(!cellLineBasalL)
			cellLineBasalL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_CELLLINEBASAL_COLUMN_MAP)
		else cellLineBasalL
	}
	
	List<Map> getEntryClones()
	{
		if(!entryClonesL)
			entryClonesL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_ENTRYCLONES_COLUMN_MAP)
		else entryClonesL
	}
	
	List<Map> getSequences()
	{
		if(!sequencesL)
			sequencesL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_SEQUENCES_COLUMN_MAP)
		else sequencesL
	}
	
	Map getSequencesMap()
	{
		if(!sequencesM)
		{
			sequencesM = [:]
			getSequences().each{seq->
				sequencesM."${seq.oldId}" = seq.sequence
			}
		}
		
		sequencesM
	}
	
	Map getProjectGeneMap()
	{
		if(!projectGeneM)
		{
			projectGeneM = [:]
			getProjectGenes().each{pg->
				projectGeneM."${pg.geneId}" = pg.projectId
			}
		}
		
		projectGeneM
	}
	
	List<Map> getPcrAmplifications()
	{
		if(!pcrAmplificationsL)
			pcrAmplificationsL = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_PCRAMPLIFICATIONS_COLUMN_MAP)
		
		pcrAmplificationsL
	}
	
	Map getPcrAmplificationsMap()
	{
		if(!pcrAmplificationsM)
		{
			pcrAmplificationsM = [:]
			getPcrAmplifications().each{
				pcrAmplificationsM."${it.pcrId}" = it.geneId
			}
		}
		
		pcrAmplificationsM
	}
	
		
	def createAll()
	{		
		createOnlyMasterData()
		
		new UserDateProcessor(this).createUsers()
		
		new ProjectsProcessor(this).createProjects()
		
		new GeneCreator(this).createGenes(false) //true / false: with / without NCBI
		
		def recombinantCreator = new RecombinantCreator(this)
		
		recombinantCreator.createRecombinantsFromStrains()
		recombinantCreator.createRecombinantsFromEntryClones()
	}
	
	def createOnlyMasterData()
	{
		def masterDataCreator = new MasterDataCreator(this)
		
		masterDataCreator.createCommonOrganisms()
		masterDataCreator.createLaboratories()
		masterDataCreator.createVectors()
		masterDataCreator.createBasicCellLineData()
		
		def storageCreator = new StorageCreator(this)
		storageCreator.createStorageManually()
	}
	

	

}
