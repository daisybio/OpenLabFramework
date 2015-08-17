/**
 * 
 */
package de.andreasschmitt.richui

import groovy.xml.MarkupBuilder

/**
 * @author Andreas Schmitt
 *
 */
public class Resource {

	String name
	StringWriter writer
	MarkupBuilder builder
	
	public Resource(){
		writer = new StringWriter()
		builder = new MarkupBuilder(writer)
	}
	
	StringWriter getWriter(){
		return writer
	}
	
	MarkupBuilder getBuilder(){
		return builder
	}
	
	String getData(){
		writer.flush()
		return writer.toString()
	}
	
	String toString(){
		name
	}
	
}
