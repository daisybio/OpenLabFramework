package org.openlab.security

class User implements Serializable{

	String username
	String userRealName
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	String email

	static constraints = {
		username blank: false, unique: true
		password blank: false
		userRealName blank: false
		email email:true
	}

	static mapping = {
		password column: '`password`'
		table 'olfUser'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	String toString()
	{
		username
	}
}
