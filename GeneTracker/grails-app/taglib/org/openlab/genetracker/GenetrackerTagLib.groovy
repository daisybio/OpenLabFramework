package org.openlab.genetracker

class GenetrackerTagLib {

	def settingsService
	
	def ncbiSequenceViewer = { attrs ->
		def url = settingsService.getSetting(key: "genetracker.ncbi.sequenceviewer.url")
		
		def gene = Gene.get(attrs.geneId) 
		
		if(url && gene.accessionNumber && (gene.geneType == "Wildtype"))
		{
			//out << """<iframe width=100% height="400" src="${url}?id=${gene.accessionNumber}#"/>"""
			out << """<object data="${url}?id=${gene.accessionNumber}#" type="text/html" width="100%" height="500">
			</object>
			"""
		 
		}
		else if(gene.geneType != "Wildtype")
		{
			out << settingsService.getLabel(key: "sequenceviewer.nourl")?:"can only be displayed for wildtype NCBI genes"
		}
		else{
			out << settingsService.getLabel(key: "sequenceviewer.nourl")?:"no valid url"
		}
	}
	
}
