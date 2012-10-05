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
}