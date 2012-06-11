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
