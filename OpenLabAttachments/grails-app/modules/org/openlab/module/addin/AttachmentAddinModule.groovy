package org.openlab.module.addin;

import org.openlab.module.*

public class AttachmentAddinModule implements AddinModule{

	def getName()
	{
		"attachments"
	}
	
	def getTemplate()
	{
		"attachmentsAddin"
	}
	
	def getPluginName()
	{
		"open-lab-attachments"
	}
	
}
