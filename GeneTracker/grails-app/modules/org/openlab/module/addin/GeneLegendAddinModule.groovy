package org.openlab.module.addin

import org.openlab.module.*;

class GeneLegendAddinModule implements AddinModule{

	def getName()
	{
		"gene legend"
	}
	
	def getTemplate()
	{
		"geneLegendAddin"
	}
	
	def getPluginName()
	{
		"gene-tracker"
	}

}
