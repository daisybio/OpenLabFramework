package org.openlab.datasource

/**
 * Controller to display information about the currently configured datasource.
 * @author markus.list
 *
 */
class DataSourceController {

    def dataSource

    def index = {
        redirect action: show
    }

    def show =
        {
            if (dataSource instanceof org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy) {
                def baseDataSource = dataSource.targetDataSource
                println baseDataSource.properties
                [url: baseDataSource.url, driverClassName: baseDataSource.driverClassName, username: baseDataSource.username]
            }

            else {
                [url: dataSource.jdbcUrl, driverClassName: dataSource.driverClass, username: dataSource.user]
            }

        }


}
