package de.andreasschmitt.richui

/*
*
* @author Andreas Schmitt
*/

interface Renderer {

	String renderTag(Map attrs, Closure body) throws RenderException

}