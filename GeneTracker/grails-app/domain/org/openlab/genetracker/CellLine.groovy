package org.openlab.genetracker

import org.openlab.main.*;

class CellLine extends MasterDataObject {

	static mapping = {
		table 'gtCellLine'
		cache true
		antibiotics cache: true
		mediumAdditives cache: true
		}
	CultureMedia cultureMedia
	String notes
	
	static searchable = {
		label boost: 1.0
	}
	
	String label
	
	static hasMany = [antibiotics: Antibiotics, mediumAdditives: MediumAdditive]
	
	String toString() 
	{
		label.toString()
	}
}
