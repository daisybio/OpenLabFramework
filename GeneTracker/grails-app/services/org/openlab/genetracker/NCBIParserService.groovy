package org.openlab.genetracker
/**
 * This service handles the NCBI efetch webservice and allows to access its data
 * through accession numbers. The data can be parsed to XML and to DomainObjects of
 * type Gene.
 * @author markus.list
 */
class NCBIParserService {
	boolean transactional = false
	
	def settingsService
	def timeOfLastNcbiRequest
	
	/**
	 * Creates a URL to access the NCBI webservice. Values taken from settings database.
	 * @param accessionNumber
	 * @return
	 */
	def createNcbiUrl(String accessionNumber)
	{
	      def url_base = settingsService.getSetting(key: "genetracker.ncbi.baseurl")
	      def url_db = "db=" + settingsService.getSetting(key: "genetracker.ncbi.db")
	      def url_rettype = "rettype=" + settingsService.getSetting(key: "genetracker.ncbi.rettype")
	      def url_retmode = "retmode=" + settingsService.getSetting(key: "genetracker.ncbi.retmode")
	      def url_accessionNumber = "id=" + URLEncoder.encode(accessionNumber)
	
	      if(url_base == "null") throw new Exception("NCBIParserService: Parameter missing in settings database: url_base")
	      
	      def query = []
	      query << url_db << url_accessionNumber << url_rettype << url_retmode
	      
	      query.each{
	    	  if(it.reverse().startsWith("llun")) throw new Exception("NCBIParserService: Parameter missing in settings database")
	      }
	      
	      new URL(url_base + query.join("&"))
	}
	
	/**
	 * Connects the NCBI webservice and acquires data.
	 * Parses the NCBI XML response via XmlSlurper.
	 * @param accessionNumber
	 * @return
	 */
    def parseNcbiToXmlSlurper(String accessionNumber) {

		  //ncbi allows 3 requests per second -> ensure enough time has passed or wait
		  if(timeOfLastNcbiRequest)
		  {
			  def elapsedTime = (new Date()).getTime() - timeOfLastNcbiRequest.getTime()
			  if(elapsedTime < 333) Thread.currentThread().sleep(333 - elapsedTime);
		  }
		  
		  timeOfLastNcbiRequest = new Date()
		  
          def url = createNcbiUrl(accessionNumber)    	
    	  
		  def connection = url.openConnection()

    	  def ncbiXML = null
    	  
	      if(connection.responseCode == 200){
	        def xml = connection.content.text
	        ncbiXML = new XmlSlurper().parseText(xml)
	      }
	      else{
	        log.error("NCBIParser parseNcbiToXmlSlurper FAILED")
	        log.error(url)
	        log.error(connection.responseCode)
	        log.error(connection.responseMessage)
	      }      
	      return ncbiXML
    }
    
    /**
     * Takes the XMLSlurper object as input and parses important
     * content into a map that is returned.
     * @param accessionNumber
     * @return
     */
    def parseNcbiXmlToMap(String accessionNumber)
    {
    	def ncbiXML = parseNcbiToXmlSlurper(accessionNumber)
    	
    	def result = [:]
    	              
        result.locus = ncbiXML.GBSeq.GBSeq_locus as String
        try{
			int l = Double.valueOf(ncbiXML.GBSeq.GBSeq_length as String).abs()
			result.length = l
        }catch(NumberFormatException e){
			result.length = 0
		}
        result.accessionNumber = ncbiXML.GBSeq.'GBSeq_primary-accession' as String
        result.strandedness = ncbiXML.GBSeq.GBSeq_strandedness as String
        result.moltype = ncbiXML.GBSeq.GBSeq_moltype as String
        result.topology = ncbiXML.GBSeq.GBSeq_topology as String
        result.division = ncbiXML.GBSeq.GBSeq_division as String
        result.description = ncbiXML.GBSeq.GBSeq_definition as String
        result.accessionVersion = ncbiXML.GBSeq.'GBSeq_accession-version' as String
        result.source = ncbiXML.GBSeq.GBSeq_source as String
        result.organism = ncbiXML.GBSeq.GBSeq_organism as String
        result.sequence = ncbiXML.GBSeq.GBSeq_sequence as String
        ncbiXML.GBSeq.'GBSeq_feature-table'.GBFeature.each{feature ->
    		//extract coding sequence region for ORF information
    		if((feature.GBFeature_key as String) == "CDS")
    		{
    			def cds = feature.GBFeature_location as String
    			try{
					def range = Eval.me(cds)
    			
					result.orfStart = range.getFromInt()
					result.orfStop = range.getToInt()
					
    			}catch(Exception e){
					result.orfStart = 0
					result.orfEnd = 0
					result.description += ";ORF: ${cds}"
				}
                                
                feature.GBFeature_quals.children().each{qual ->

                	if((qual.GBQualifier_name as String) == "gene")
    					result.name = qual.GBQualifier_value as String
    					
					else if((qual.GBQualifier_name as String) == "gene_synonym")
    					result.synonym = qual.GBQualifier_value as String
    				
					else if((qual.GBQualifier_name as String) == "product")
						result.product = qual.GBQualifier_value as String
						
					else if((qual.GBQualifier_name as String) == "protein_id")
						result.proteinId = qual.GBQualifier_value as String
    			}
    		}

    	}
        return result
    }
}
