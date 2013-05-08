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
package org.openlab.settings;

import groovy.sql.*;

/**
 * Tests the SettingsService and its cooperation with the setting database
 * in various usecases
 * @author markus.list
 *
 */
class SettingsServiceTests extends grails.test.GrailsUnitTestCase {
	
	def settingsService
	def springSecurityService
	def key = "key"
	def value = "value"

    protected void setUp() {
		super.setUp()
		settingsService.prepareDatabase(tableName: 'JUnitTest')
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testSetAndGetSetting(){
    	settingsService.setSetting(key: key, value: value)
    	assertEquals(value, settingsService.getSetting(key: key)) 
    }
    
	void testGetSettingsDataSet(){
		testSetAndGetSetting()
		assertNotNull(settingsService.getSettingsDataSet())
	}
	
	void testSetAndGetDefaultSetting(){
		settingsService.setDefaultSetting(key: key, value: value)
		assertEquals(value, settingsService.getDefaultSetting(key: key))			
	}
	
	void testSetUserAndGetDefaultSetting(){
		settingsService.setDefaultSetting(key: key, value: value)
		assertEquals(value, settingsService.getUserSetting(key: key))
	}

	void testExists(){
		settingsService.setSetting(key: key, value: value)
		assertTrue(settingsService.exists(key: key))
	}
	
	void testExistsNot(){
		assertFalse(settingsService.exists(key: "differentKey"))
	}
		
	void testDeleteSetting(){
		settingsService.setSetting(key: key, value: value)
		assertEquals(value, settingsService.getSetting(key: key))
		settingsService.deleteSetting(key: key)
		assertFalse(settingsService.exists(key: key))
		assertNull(settingsService.getSetting(key: key))
	}
	
	void testSetAndGetLabelInLanguage(){
		settingsService.setLabelInLanguage(key: key, lang: "german", value: value)
		assertEquals(value, settingsService.getLabelInLanguage(key: key, lang: "german"))
	}
	
	void testDefaultLanguageLabel(){
		testSetAndGetLabelInLanguage()
		settingsService.setDefaultSetting(key: "language", value: "german")
		assertEquals(value, settingsService.getLabel(key: key))
	}
	
	void testUpdateSetting(){
		settingsService.setSetting(key: key, value: value)
		settingsService.setSetting(key: key, value: "anothervalue")
		assertEquals("anothervalue", settingsService.getSetting(key: key))
	}
	
	void testLowerCase(){
		settingsService.setSetting(key: "KeY", value: value)
		assertEquals(value, settingsService.getSetting(key: key))
	}
	
	void testLabelReplacement(){
		settingsService.setLabel(key: key, value: "Hallo %1% und %2% und %3%%4% %5 %45% %A%")
		def anticipatedResult = settingsService.getLabel(key: key, variables: ["Erde", "Mars", "Venus", "Merkur", "Sonne"])
		assertEquals("Hallo Erde und Mars und VenusMerkur %5 %45% %A%", anticipatedResult)
	}
	
	void testLabelReplacementNothingHappens(){
		settingsService.setLabel(key: key, value: "Hallo %1% und %2%")
		assertEquals("Hallo %1% und %2%", settingsService.getLabel(key: key))
	}
	
	void testLabelEmptyReplacement(){
		settingsService.setLabel(key: key, value: "Hallo %1% und %2%")
		assertEquals("Hallo Welt und ", settingsService.getLabel(key: key, variables: ["Welt"]))
	}
}
