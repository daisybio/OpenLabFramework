package org.openlab.genetracker;

/**
 * Tests each method of NCBIParserService.
 * @author markus.list
 *
 */
class NCBIParserServiceTest extends grails.test.GrailsUnitTestCase {

	def NCBIParserService
	def settingsService
	String accessionNr = "BC014175"
	
	/**
	 * create an url from database parameters.
	 */
	void testCreatUrl(){
		assertEquals("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=BC014175&rettype=gb&retmode=xml" , 
				NCBIParserService.createNcbiUrl(accessionNr).toString())
	}
	
	/**
	 * check if the XmlSlurper returns a non-null object.
	 */
	void testParseNcbiToXmlSlurperNotNull(){
		def ncbiXML = NCBIParserService.parseNcbiToXmlSlurper(accessionNr)
		assertNotNull(ncbiXML)
	}
	
	/**
	 * checks if the XmlSlurper object contains accessible data.
	 */
	void testParseNcbiToXmlSlurperCorrectness(){
		def ncbiXML = NCBIParserService.parseNcbiToXmlSlurper(accessionNr)
		assertEquals("871", ncbiXML.GBSeq.GBSeq_length as String) 
	}
	
	/**
	 * checks if the handling of special case ('-') works. Dashes cause problems
	 * unless the string is embraced by hyphens.
	 */
	void testParseNcbiToXmlSlurperCorrectnessWithDash(){
		def ncbiXML = NCBIParserService.parseNcbiToXmlSlurper(accessionNr)
		assertEquals(accessionNr, (ncbiXML.GBSeq.'GBSeq_primary-accession' as String))
	}
	
	/**
	 * checks if all fields of the map are set correctly.
	 */
	void testParseNcbiXmlToMap(){
		def result = NCBIParserService.parseNcbiXmlToMap(accessionNr)
		assertEquals("gctgcggccgcccgcgcggacccggcgagaggcggcggcgggagcggcggtgatggacgggtccggggagcagcccagaggcggggggcccaccagctctgagcagatcatgaagacaggggcccttttgcttcagggtttcatccaggatcgagcagggcgaatggggggggaggcacccgagctggccctggacccggtgcctcaggatgcgtccaccaagaagctgagcgagtgtctcaagcgcatcggggacgaactggacagtaacatggagctgcagaggatgattgccgccgtggacacagactccccccgagaggtctttttccgagtggcagctgacatgttttctgacggcaacttcaactggggccgggttgtcgcccttttctactttgccagcaaactggtgctcaaggccctgtgcaccaaggtgccggaactgatcagaaccatcatgggctggacattggacttcctccgggagcggctgttgggctggatccaagaccagggtggttgggacggcctcctctcctactttgggacgcccacgtggcagaccgtgaccatctttgtggcgggagtgctcaccgcctcactcaccatctggaagaagatgggctgaggcccccagctgccttggactgtgtttttcctccataaattatggcatttttctgggaggggtggggattgggggacatgggcatttttcttacttttgtaattattggggggtgtggggaagagtggtcttgagggggtaataaacctccttcgggacacaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				result.sequence)
		assertEquals("BAX", result.name)
		assertEquals(871, result.length)
		assertEquals(accessionNr, result.accessionNumber)
		assertEquals("single", result.strandedness)
		assertEquals("mRNA", result.moltype)
		assertEquals("linear", result.topology)
		assertEquals("PRI", result.division)
		assertEquals("Homo sapiens BCL2-associated X protein, mRNA (cDNA clone MGC:20956 IMAGE:4578562), complete cds",
				result.definition)
		assertEquals(accessionNr+".2", result.accessionVersion)
		assertEquals("Homo sapiens (human)", result.source)
		assertEquals("Homo sapiens", result.organism)
	}
	
	/**
	 * removes a necessary entry from the database and checks if url creation
	 * fails correctly
	 */
	void testCreateUrlFail(){
		def key = "genetracker.ncbi.db"
		def backupValue = settingsService.getSetting(key: key)
		settingsService.deleteSetting(key: key)
		
		try{
			NCBIParserService.createNcbiUrl(accessionNr)
		}
		catch(Exception e){
			settingsService.setSetting(key: key, value: backupValue)
			return;
		}
		fail("expected exception")
	}
}
