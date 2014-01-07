package org.openlab.application;

import java.util.Map;

public interface Application 
{
	public abstract String getApplicationName();	
	
	public abstract Map getApplicationMenu();
	
	public abstract Map getMasterDataMenu();
	
	public abstract Map getAdministrationMenu();
		
	public abstract boolean isActivated();
		
	public abstract void activate(boolean b);
	
	public abstract String getDescription();
	
	public abstract String getController(String label);
	
}
