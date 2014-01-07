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
package org.openlab.taqman

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import java.io.* ;

/**
 * This service allows for establishing a connection to an R server. Furthermore, it allows to transfer files both ways
 * @author mlist
 *
 */
class RperationsService {

	final static String host = CH?.config?.openlab?.Rserve?.host?:"127.0.0.1"
	final static int port = Integer.valueOf(CH?.config?.openlab?.Rserve?.port?:"6311")
	
	def getConnection = {
        log.info "trying to connect to Rserve on ${host}:${port}"
		return new RConnection(host, port)
	}
	
	def getConnectionWithParams = { host, port ->
		return new RConnection(host, port)
	}
	
	def testConnection = { connection ->
		connection.eval("R.version.string").asString();
	}
	
	def transferToServer(RConnection r, String client_file, String server_file ){
		
		byte [] b = new byte[8192];
		try{
		  /* the file on the client machine we read from */
		  BufferedInputStream client_stream = new BufferedInputStream(
			new FileInputStream( new File( client_file ) ) );
		  
		  /* the file on the server we write to */
		  RFileOutputStream server_stream = r.createFile( server_file );
		  
		  /* typical java IO stuff */
		  int c = client_stream.read(b) ;
		  while( c >= 0 ){
			server_stream.write( b, 0, c ) ;
			c = client_stream.read(b) ;
		  }
		  server_stream.close();
		  client_stream.close();
		  
		} catch( IOException e){
		  log.error e.getMessage()
		}
		
	  }
	  
	  public void transferToClient(RConnection r,  String client_file, String server_file ){
		
		byte [] b = new byte[8192];
		try{
		  
		  /* the file on the client machine we write to */
		  BufferedOutputStream client_stream = new BufferedOutputStream(
			new FileOutputStream( new File( client_file ) ) );
		  
		  /* the file on the server machine we read from */
		  RFileInputStream server_stream = r.openFile( server_file );
		  
		  /* typical java io stuff */
		  int c = server_stream.read(b) ;
		  while( c >= 0 ){
			client_stream.write( b, 0, c ) ;
			c = server_stream.read(b) ;
		  }
		  client_stream.close();
		  server_stream.close();
		  
		} catch( IOException e){
		  log.error e.getMessage()
		}
		
	  }
	
}
