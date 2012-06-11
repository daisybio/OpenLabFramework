package org.openlab.security

class Role {

	String authority

	static mapping = {
		cache true
		table 'olfRole'
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
