package de.andreasschmitt.richui;
import groovy.xml.MarkupBuilder

/*
*
* @author Andreas Schmitt
*/
class PortletViewRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {				
		builder."div"("class": "slot ${attrs?.slotClass}", id: "slot_${attrs.page}_${attrs?.id}", style: "${attrs?.slotStyle}"){
			"div"("class": "player visiblePlayer ${attrs?.playerClass}", id: "${attrs?.id}", style: "${attrs?.playerStyle}"){
				builder.yield("${body.call()}", false)
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			

	}

}