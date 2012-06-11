package org.openlab.security

class UserGroup {

	String description
	
	static hasMany = [users: User]
    static mapping = {
		table 'olfUserGroup'
	}
}
