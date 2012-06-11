package org.openlab.security

class Requestmap {

	String url
	String configAttribute

	static mapping = {
		cache true
		table 'olfRequestmap'
	}

	static constraints = {
		url blank: false, unique: true
		configAttribute blank: false
	}
}
