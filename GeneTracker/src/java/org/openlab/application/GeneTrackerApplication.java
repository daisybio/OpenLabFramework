package org.openlab.application;

import java.util.HashMap;
import java.util.Map;

public class GeneTrackerApplication implements Application{
	
	public static final String applicationName = "GeneTracker";
	
	private boolean activated;
	
	public String getApplicationName()
	{
		return this.applicationName;
	}	
	
	public Map getApplicationMenu()
	{
		Map map = new HashMap();
		map.put("List Genes", "list");
		map.put("Create Gene", "create");
		map.put("Create CellLine Data", "create");
		map.put("List CellLine Data", "list");
		return map;
	}
	
	public Map getMasterDataMenu()
	{
		Map map = new HashMap();
		map.put("Organisms", "list");
		map.put("Goodies", "list");
		map.put("Vectors", "list");
		map.put("Origins", "list");
		map.put("CellLine", "list");
		map.put("CultureMedia", "list");
		map.put("Antibiotics", "list");
		return map;
	}
	
	public Map getAdministrationMenu()
	{
		return null;
	}
	
	public String getController(String label)
	{
		if(label.equals("Organisms")) return "organism";
		else if(label.equals("Goodies")) return "goody";
		else if(label.equals("Vectors")) return "vector";
		else if(label.equals("Origins")) return "origin";
		else if(label.equals("CellLine")) return "cellLine";
		else if(label.equals("CultureMedia")) return "cultureMedia";
		else if(label.equals("Create CellLine Data") || label.equals("List CellLine Data")) return "cellLineData";
		else if(label.equals("Antibiotics")) return "antibiotics";
		else return "gene";
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
		return "An application to keep track of genes and associated experiments.";
	}
	
}
