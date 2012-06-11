package org.openlab.dataimport

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory

class GeneTrackerImportClass {

	public def importer
	
   /*
	* Create Logger
	*/
    static Log log = LogFactory.getLog(GeneTrackerImporter.class)
	
	public GeneTrackerImportClass(GeneTrackerImporter importer)
	{
		this.importer = importer
	}
}
