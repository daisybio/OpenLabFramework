package org.openlab.testing;

import grails.test.*

public class RperationsServiceTests extends GrailsUnitTestCase {

	def RperationsService
	
	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}
	
	void testConnect(){
		def c = RperationsService.getConnection()
		def x = c.eval("R.version.string").asString()
		assertEquals "R version 2.14.0 (2011-10-31)", x
	}
	
	void testReConnect(){
		def c = RperationsService.getConnection()
		def rSession = c.detach()
		def newC = rSession.attach()
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
