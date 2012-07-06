import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.apache.commons.dbcp.BasicDataSource

beans = {

/**
 * Default DataSource configuration if no other config is found. 
 */
    settingsDataSource(BasicDataSource) {
        driverClassName = "org.hsqldb.jdbcDriver"
        url = "jdbc:hsqldb:file:olfSettingsDB"
        username = "sa"
        password = ""
    }

    /**
     * @author: markus.list
     * Wrapping c3p0 connection pool around jdbc datasource using original configuration.
     */
    /*dataSource(ComboPooledDataSource) { bean ->
        bean.destroyMethod = 'close'
        //use grails' datasource configuration for connection user, password, driver and JDBC url
        user = CH.config.dataSource.username
        password = CH.config.dataSource.password
        driverClass = CH.config.dataSource.driverClassName
        jdbcUrl = CH.config.dataSource.url
        //connection test settings
        idleConnectionTestPeriod = 2 * 60 * 60 //2 hours
        testConnectionOnCheckin = true
    }     */

}