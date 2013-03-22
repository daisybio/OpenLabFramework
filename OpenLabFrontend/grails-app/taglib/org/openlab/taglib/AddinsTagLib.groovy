package org.openlab.taglib;

import org.openlab.genetracker.*;

class AddinsTagLib {
	
	def settingsService
	def moduleHandlerService
	
	static namespace = 'addin'	
		
	def layoutAddins = { attrs, body ->
		out << "<table style='width: 250px; border: none;'>"
        def numberOfViews = settingsService.getUserSetting(key: "addins.number")

		for(int i = 1; i <= Integer.valueOf(numberOfViews); i++)
		{
			out << "<tr><td>" + richui.portletView(id:"${i}", slotStyle:'width: 250px; height: 200px;', playerStyle:'width: 250px; height: 200px;'){contentAtPos(slot:i);}
			out << "</td></tr>"
		}
		out << "</table>"
	}
	
	def contentAtPos = { attrs ->
		
		def i = attrs.slot
		
		def content = settingsService.getUserSetting(key: "addins.slot.${i}")
		
		if(content)
		{
			def addins = moduleHandlerService.getAddinModules()
			if(!addins)return content
			
			def addin = addins.find{it.getName().toString() == content.toString()}

			if(addin)
			{
				String r = g.render(template: "/addins/${addin.getTemplate()}", plugin: addin.getPluginName(), model: [slot: "${i}"])
				out << r
			}			
		
			else
			{
				 out << content.toString()
			}
		}
		else
		{
			out << "empty slot"
		}
	}
}
