/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.openlab.settings

import groovy.sql.Sql

/**
 * Service Class to handle application settings 
 * @author markus.list
 *
 */
class SettingsService {

	def springSecurityService
	
	static transactional = false

	/**
	 * Dataset representation of settings datatable for easy iteration
	 */
	def getSettingsDataSet = {

		UserSetting.list()
		//sql.dataSet("olfSettings")
	}

	/**
	 * Get and set default settings
	 */
	def setDefaultSetting = { attrs ->
		setSetting(key: "default.${attrs.key}", value: attrs.value)
	}
	
	def getDefaultSetting = { attrs ->
		getSetting(key: "default.${attrs.key}")
	}
	
	/**
	 * Get and set user specific user settings
	 */
	def getUserSetting = { attrs ->
		def username = springSecurityService.getPrincipal()?.username
		def userSetting = getSetting(key: "user.${username}.${attrs.key}")
		if((username == null) || !userSetting)
		{
			return getDefaultSetting(key: attrs.key)
		}
		else
		{
			return userSetting
		}
	}
	
	def setUserSetting = { attrs -> 
		def username = springSecurityService.getPrincipal()?.username
		if(username != null) setSetting(key: "user.${username}.${attrs.key}", value: attrs.value)
		else return false
	}
	
	/**
	 * Get and set or delete setting using fully qualified key
	 */
	def exists = { attrs ->
		def exists = getSetting(key: attrs.key)
		if(exists) return true
		else return false
	}
	
	def getSetting = { attrs ->
		//prepare key
		def lowerCaseKey = attrs.key.toLowerCase()
		
		//def sql = new Sql(settingsDataSource)
		def result = UserSetting.get(lowerCaseKey)
		//sql.firstRow("select * from olfSettings where key= ?;", [lowerCaseKey])
		if(!result) return null
		else return result.value
	}
	
	def setSetting = { attrs ->
		
		if(!attrs.value) return false
		
		//def sql = new Sql(settingsDataSource)
		def lowerCaseKey = attrs.key.toLowerCase()
		
		if(exists(key: lowerCaseKey))
		{
			UserSetting.get(lowerCaseKey).value = attrs.value
			//sql.execute("update olfSettings set value = ? where key= ?;", [attrs.value, lowerCaseKey])
		}
		else
		{
			new UserSetting(key: lowerCaseKey, value: attrs.value).save(flush: true)
			//sql.execute("insert into olfSettings values(?, ?);", [lowerCaseKey, attrs.value])
		}
	}
	
	def deleteSetting = { attrs ->
		//def sql = new Sql(settingsDataSource)
		
		def lowerCaseKey = attrs.key.toLowerCase()
		
		if(exists(key: attrs.key))
		{
			//sql.execute("delete from olfSettings where key=?;", [lowerCaseKey])
            UserSetting.get(lowerCaseKey).delete()
		}
	}
	
	/**
	 * Get and set labels
	 */
	
	/**
	 * returns a label for a key and language attribute (lang)
	 */
	def getLabelInLanguage = { attrs ->
		def label = getSetting(key: "label.${attrs.lang}.${attrs.key}")
		if(attrs.variables && label != null)
		{
			label.replaceAll("[%][1-9][%]",{String it -> 
				def position = Integer.valueOf(it.substring(1,2))
				def variables = attrs.variables
				if(position <= variables.size()) variables.get(position-1) 
				else return ""
			})
		}
		else return label
	}
	
	/*
	 * returns a label for a given key using the default setting for language (or english)
	 */
	def getLabel = { attrs->
		def defaultLanguage = getDefaultSetting(key: "language")?:'english'
		getLabelInLanguage(lang: defaultLanguage, key: "${attrs.key}", variables: attrs?.variables)
	}

	def setLabel = { attrs->
		def defaultLanguage = getDefaultSetting(key: "language")?:'english'
		setLabelInLanguage(key: "${attrs.key}", lang: "${defaultLanguage}", value: attrs.value)
	}
	
	def setLabelInLanguage ={ attrs->
		setSetting(key: "label.${attrs.lang}.${attrs.key}", value: attrs.value)
	}
}
