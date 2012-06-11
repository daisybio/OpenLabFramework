package org.openlab.storage

class StorageLocation {

	int zip
	String city
	String street
	String country
	int number
	int floor
	int room
	String description
	
	String toString()
	{
		description
	}
    static constraints = {
		description()
		street(nullable:true)
		city(nullable:true)
		number(nullable:true)
		country(nullable:true)
		floor(nullable:true)
		room(nullable:true)
		zip(nullable:true)
    }
}
