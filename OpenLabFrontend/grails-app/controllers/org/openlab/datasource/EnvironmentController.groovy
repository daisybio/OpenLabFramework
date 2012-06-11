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

