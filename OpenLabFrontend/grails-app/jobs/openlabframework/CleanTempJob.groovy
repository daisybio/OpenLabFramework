package openlabframework

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class CleanTempJob {
    def static triggers = {
		cron name: 'myTrigger', cronExpression: "0 0 4 * * ?" 
	}
	def group = "MyGroup"

    def execute() {
		
		def directoryName = CH?.config?.openlab?.temp?.dir
		
		if(directoryName != null)
		{
			File directory = new File(directoryName);
		
			// Get all files in directory
		
			File[] files = directory.listFiles();
			for (File file : files)
			{
				// Delete each file
		
				if (!file.delete())
				{
					// Failed to delete file
		
					log.error("Failed to delete "+file);
				}
			}
			log.info "Deleted files in temporary directory"
		}
		
		else
		{
			log.error "Could not delete temporary files, folder not specified in openlab config."
		}
    }
}
