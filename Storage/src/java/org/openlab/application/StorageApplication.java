package org.openlab.application;

import java.util.HashMap;
import java.util.Map;

public class StorageApplication implements Application{
	
	public static final String applicationName = "Storage";
	
	private boolean activated;
	
	public String getApplicationName()
	{
		return this.applicationName;
	}	
	
	public Map getApplicationMenu()
	{
		Map map = new HashMap();
		map.put("Storages", "list");
		return map;
	}
	
	public Map getMasterDataMenu()
	{
		Map map = new HashMap();
		map.put("Storage Locations", "list");
		return map;
	}
	
	public Map getAdministrationMenu()
	{
		return null;
	}
	
	public String getController(String label)
	{
		if(label.equals("Storage Locations")) return "storageLocation";
		else return "storage";
	}

	@Override
	public void activate(boolean b) 
	{
		this.activated = b;		
	}

	@Override
	public boolean isActivated() 
	{
		return this.activated;
	}

	@Override
	public String getDescription() 
	{
		return "An application to keep track of a modularized, arbitrary complex storage system.";
	}
	
}
