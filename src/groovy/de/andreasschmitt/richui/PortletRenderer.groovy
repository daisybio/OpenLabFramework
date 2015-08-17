package de.andreasschmitt.richui

import groovy.xml.MarkupBuilder

/*
*
* @author Andreas Schmitt
*/
class PortletRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		
		builder."div"(){
			builder.yield("${body.call()}", false)	
		}
		
		if(!attrs?.readOnly || attrs.readOnly == "false"){
			builder.script(type: "text/javascript"){
				builder.yield("	var slots = [], players = [],\n", false)
				builder.yield("	YUIEvent = YAHOO.util.Event, DDM = YAHOO.util.DDM;\n", false)
							
				builder.yield("	YUIEvent.onDOMReady(function() {\n", false) 				
				//	slots
				attrs.views.each { view ->
					builder.yield("		slots[${view}] = new YAHOO.util.DDTarget('slot_${attrs.page}_${view}', 'bottomslots');\n", false)
					builder.yield("		players[${view}] = new YAHOO.example.DDPlayer('${view}', 'bottomslots', {action: '${attrs.action}'});\n", false)
					builder.yield("	    slots[${view}].player = players[${view}];\n", false)
					builder.yield("	    players[${view}].slot = slots[${view}];\n", false)	
				}
								    
				// players
				builder.yield("	YUIEvent.on('ddmode', 'change', function(e) {\n", false)
				builder.yield("	YAHOO.util.DDM.mode = this.selectedIndex;\n", false)
				builder.yield("	   });\n", false)
				builder.yield("   });\n", false)
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/portlet.css")
				css.name = "${resourcePath}/css/portlet.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/portlet.css")
			css.name = "${resourcePath}/css/portlet.css"
		}
		
		resources.add(css)
		
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)
		

		// Yahoo drag drop min
		Resource yahooDragDropMin = new Resource()
		yahooDragDropMin.name = "${yuiResourcePath}/dragdrop/dragdrop-min.js"
		
		def yahooDragDropMinBuilder = yahooDragDropMin.getBuilder()
		yahooDragDropMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/dragdrop/dragdrop-min.js", "")
		
		resources.add(yahooDragDropMin)
		

		// Yahoo min
		Resource yahooMin = new Resource()
		yahooMin.name = "${yuiResourcePath}/yahoo/yahoo-min.js"
		
		def yahooMinBuilder = yahooMin.getBuilder()
		yahooMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo/yahoo-min.js", "")
		
		resources.add(yahooMin)
		

		// Yahoo event min
		Resource yahooEventMin = new Resource()
		yahooEventMin.name = "${yuiResourcePath}/event/event-min.js"
		
		def yahooEventMinBuilder = yahooEventMin.getBuilder()
		yahooEventMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/event/event-min.js", "")
		
		resources.add(yahooEventMin)
		
		
		// Yahoo connection min
		Resource yahooConnectionMin = new Resource()
		yahooConnectionMin.name = "${yuiResourcePath}/connection/connection-min.js"
		
		def yahooConnectionMinBuilder = yahooConnectionMin.getBuilder()
		yahooConnectionMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/connection/connection-min.js", "")
		
		resources.add(yahooConnectionMin)
		

		// Portlet
		Resource portlet = new Resource()
		portlet.name = "${resourcePath}/js/portlet/portlet.js"
		
		def portletBuilder = portlet.getBuilder()
		portletBuilder.script(type: "text/javascript", src: "${resourcePath}/js/portlet/portlet.js", "")
		
		resources.add(portlet)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yield("<!-- Portlet -->", false)
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/portlet.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/portlet.css")
		}
		
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/dragdrop/dragdrop-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo/yahoo-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/event/event-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/connection/connection-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/portlet/portlet.js", "")
	}
}