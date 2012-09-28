package org.openlab.genetracker

import org.openlab.main.*;
import java.util.Date;
import org.openlab.security.User;

class Viability extends SubDataObject
{
	static mapping = {
		table 'gtViability'
		cache true
		}
	static belongsTo = [gene: Gene]
    CellLineData cellLineData
	Gene gene
	Date date
	Double percentage
	User researcher
	String notes
	
	static constraints = {
		cellLineData(nullable:true)
	}
	
	String toString()
	{
		if(!cellLineData) return "Viability: incomplete"
		else return "Viability of " + cellLineData + " on " + date +" : " + percentage + "%" 
	}
	
	static String type = "viability"
	static String typeLabel = "Viability"
}
