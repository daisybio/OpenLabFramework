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
package org.openlab.datasource
	
import javax.servlet.http.HttpServletResponse
import org.codehaus.groovy.grails.commons.ApplicationAttributes
import org.codehaus.groovy.grails.web.context.ServletContextHolder

/**
 * @author: markus.list
 * Code needed for switchable database approach 
 * (http://www.leebutts.com/2008/07/switchable-grails-datasource.html)
 */
class EnvironmentController {

	def show = {
			render view:'environment'
	}
	
    def change = {
        if (params.environment) {
            def env = Environment.list()
                     .find {it.id == new Integer(params.environment)}
            if (env) {
                if (env.passwordRequired) {
                    if (params.password) {
                        //take a copy and add a pword
                        env = addPasswordToEnvCopy(params, env)
                    } else {
                        render 'PASSWORD REQUIRED'
                        response.setStatus(
                          HttpServletResponse.SC_UNAUTHORIZED)
                        return
                    }
                }

                //test connection
                def oldEnv = EnvironmentHolder.getEnvironment()
                EnvironmentHolder.setEnvironment env
                def ds = getDataSourceForEnv(env)
                try {
                    def con = ds.getConnection()
                    session.environment = env
                    render 'Environment change complete.'
                } catch (e) {
                    EnvironmentHolder.setEnvironment oldEnv
                    render 'Unable to connect to database: '
                              + e.message
                    response.setStatus(
                       HttpServletResponse.SC_UNAUTHORIZED)
                    return
                }
            } else {
                render 'No such environment'
                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST)
            }
        } else {
            render 'Missing parameter environment'
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
	        }
	    }

    private def getDataSourceForEnv(env) {
        def servletContext = ServletContextHolder.servletContext
        def ctx = servletContext
                    .getAttribute(
                         ApplicationAttributes.APPLICATION_CONTEXT)
        return ctx.dataSource
    }

    private Map addPasswordToEnvCopy(Map params, env) {
        def myEnv = [:]
        env.each {key, val ->
            myEnv[key] = val
        }
        myEnv.password = params.password
        return myEnv
    }
}	

