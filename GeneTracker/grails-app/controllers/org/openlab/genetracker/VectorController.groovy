package org.openlab.genetracker

import org.openlab.genetracker.vector.*;

class VectorController {

	def scaffold = Vector
	def grailsApplication
	
    def save = {  	
		
		def className = "Vector"
		
		switch(params.type)
		{
			case "Acceptor":
				className = Acceptor.class.getName()
				break;
			
			case "Integration (First)":
				className = IntegrationFirst.class.getName()
				break;
			
			case "Integration (Second)":
				className = IntegrationSecond.class.getName()
				break;
			
			case "Cloning Vector":
				className= CloningVector.class.getName()
				break;
		}
		
		def vector = grailsApplication.getDomainClass(className).newInstance()
		vector.properties = params
		
        if (vector.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'vector.label', default: 'Vector'), vector.id])}"
            redirect(action: "show", id: vector.id, params: params)
        }
        else {
        	params.bodyOnly = true
			render(view: "create", model: [vector:vector])
        }
    }
}
