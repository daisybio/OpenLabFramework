package de.andreasschmitt.richui

import groovy.xml.MarkupBuilder

/*
 * @author Andreas Schmitt
 */
abstract class AbstractRenderer implements Renderer {
	
	public String renderResources(Map attrs, String contextPath) throws RenderException {
		try {
			StringWriter writer = new StringWriter()
			def builder = new groovy.xml.MarkupBuilder(writer)
			String resourcePath = RenderUtils.getResourcePath("Richui", contextPath)
			
			renderResourcesContent(attrs, builder, resourcePath)
			
			writer.flush()
			return writer.toString()
		}
		catch(Exception e){
			throw new RenderException("Error rendering resources with attrs: ${attrs}", e)
		}
	}
	
	public List<Resource> getResources(Map attrs, String contextPath) throws RenderException {
		try {
			String resourcePath = RenderUtils.getResourcePath("Richui", contextPath)
			return getComponentResources(attrs, resourcePath)
		}
		catch(Exception e){
			throw new RenderException("Error rendering resources with attrs: ${attrs}", e)
		}
	}
	
	public String renderTag(Map attrs) throws RenderException {
		try {			
			StringWriter writer = new StringWriter()
			def builder = new MarkupBuilder(writer)
			
			renderTagContent(attrs, builder)
			
			writer.flush()
			return writer.toString()
		}
		catch(Exception e){
			throw new RenderException("Error rendering with attrs: $attrs", e)
		}
	}
	
	public String renderTag(Map attrs, Closure body) throws RenderException {
		try {			
			StringWriter writer = new StringWriter()
			def builder = new MarkupBuilder(writer)
			
			renderTagContent(attrs, body, builder)
			
			writer.flush()
			return writer.toString()
		}
		catch(Exception e){
			throw new RenderException("Error rendering with attrs: $attrs", e)
		}
	}
	
	protected abstract void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException
	protected abstract void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException
	protected abstract void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException
	protected abstract List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException

}