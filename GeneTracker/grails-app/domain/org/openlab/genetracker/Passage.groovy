package org.openlab.genetracker

import org.openlab.main.*;
import org.openlab.security.*;

class Passage extends SubDataObject{

	static mapping = {
		table 'gtPassage'
		cache true
		}
	static belongsTo = [cellLineData: CellLineData]
    CellLineData cellLineData
    String passageNr
    Date date
    User researcher
    String notes
	
	static String type = "passage"
	static String typeLabel = "Passage"
    
    String toString()
	{
		passageNr
	}
}
