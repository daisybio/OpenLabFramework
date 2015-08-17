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
package openlabframework

class CleanTempJob {
    def static triggers = {
		cron name: 'myTrigger', cronExpression: "0 0 4 * * ?" 
	}
	def group = "MyGroup"

    def execute() {
		
		def directoryName = grails.util.Holders.config?.openlab?.temp?.dir
		
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
