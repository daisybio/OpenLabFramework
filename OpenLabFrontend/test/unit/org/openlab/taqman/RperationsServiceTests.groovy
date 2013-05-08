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
package org.openlab.taqman;
import grails.test.*
import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;
import java.io.* ;

public class RperationsServiceTests extends GrailsUnitTestCase {

	def c
	
	protected void setUp() {
		super.setUp()
		c = new RConnection("10.160.0.49", 6311)
		//c = new RConnection("localhost", 6311)
	}

	protected void tearDown() {
		super.tearDown()
		c.close()
	}
	
	void testConnect(){
		def x = c.eval("R.version.string").asString()
		assertEquals "R version 2.14.0 (2011-10-31)", x
	}
	
	void testReConnect(){
		def rSession = c.detach()
		println rSession.host
		println rSession.port
		rSession.port = 6311
		c = new RConnection(rSession)
		def x = c.eval("R.version.string").asString()
		assertEquals "R version 2.14.0 (2011-10-31)", x
	}
	
	void testTransferToServer() {
		fail("Not yet implemented");
	}

	void testTransferToClient() {
		fail("Not yet implemented");
	}

}
