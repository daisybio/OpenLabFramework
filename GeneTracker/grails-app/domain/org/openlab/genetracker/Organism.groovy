package org.openlab.genetracker

import org.openlab.main.*;

class Organism extends MasterDataObject{
    
	static mapping = {
		table 'gtOrganism'
		cache true
		}
	
    String name
    String description    
}
