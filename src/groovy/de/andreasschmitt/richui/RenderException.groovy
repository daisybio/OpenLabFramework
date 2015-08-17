package de.andreasschmitt.richui

/*
 * @author Andreas Schmitt
 */
class RenderException extends Exception {
	
	public RenderException(){
		super()
	}
	
	public RenderException(String message){
		super(message)
	}
	
	public RenderException(Throwable throwable){
		super(throwable)
	}
	
	public RenderException(String message, Throwable throwable){
		super(message, throwable)
	}
}