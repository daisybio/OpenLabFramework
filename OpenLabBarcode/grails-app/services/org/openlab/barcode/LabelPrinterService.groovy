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
package org.openlab.barcode

class LabelPrinterService {

	def settingsService
	
	def sendToLabelPrinter(def printerParams)
	{
		def urlBase = settingsService.getDefaultSetting(key: "barcode.baseUrl")
	 	
		def url = new URL(urlBase + "print?" + printerParams.join("&"))
		
		println url;
		def timeoutMs 
		try{
			timeoutMs = Integer.valueOf(settingsService.getDefaultSetting(key: "barcode.connectionTimeout")?:5000)
		}catch(NumberFormatException e)
		{
			timeoutMs = 5000
		}
		
		println timeoutMs 
		
		def connection 
		
		try{
			println url
			connection = url.openConnection()
			println connection
			
			//connection.setConnectTimeout(timeoutMs);
			//connection.setReadTimeout(timeoutMs);
		}catch(Exception e){
			throw e
			log.error("Could not connect ${url}: c${e.getMessage()}")
			return false
		}
		println connection.responseCode
		if(connection.responseCode == 200){
			log.info("Barcode Label successfully sent to printer: REST service via ${url}")
			return true;
		}
		
		else{
			log.error("Sending barcode label to printer REST service via ${url} FAILED")
			return false;
		}
	}
}
