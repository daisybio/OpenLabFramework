package org.openlab.storage

/**
 * A box is 
 * the smallest compartment in the storage concept.
 * It contains StorageElements that are linked to 
 * coordinates of the box as well as DataObjects.
 * @author markus.list
 *
 */
class Box {

	//capacity can easily be calculated and does not need to be persisted
	static transients = ["capacity"]
	
	def capacity = {
		(xdim * ydim)
    }
    int xdim
    int ydim
    
    String description
    Compartment compartment
    static hasMany = [elements : StorageElement]
    static belongsTo = Compartment
	
    //A storage element should not exist without a box, therefore cascade on delete.
    static mapping = {
    	elements cascade:"all-delete-orphan"
    }
    
	Date lastUpdate
	
	static constraints = {
    	xdim(max:100)
    	ydim(max:100)
		lastUpdate(nullable:true)
		description(unique:'compartment')
    }
    
    String toString()
    {
    	"${compartment} - ${description}"
    }
	
	def beforeInsert()
	{
		lastUpdate = new Date()
	}
    def beforeUpdate(){
        lastUpdate = new Date()
    }
}


