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
