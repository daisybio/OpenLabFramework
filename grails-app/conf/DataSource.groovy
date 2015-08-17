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

hibernate {
    //flush.mode = 'commit'
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    format_sql = true
    use_sql_comments = true
}

// set per-environment serverURL stem for creating absolute links
// def cloudEnv = new CloudEnvironment()

environments {
    production {
    }
    development {
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    /*cloud {
        dataSource {

            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            pooled = true

            if(cloudEnv.isCloudFoundry()) {

                println "detected cloud environment, trying to extract DB settings."
                def dbInfo = cloudEnv.getServiceInfo('cleardb-94aee', RdbmsServiceInfo.class)

                url = dbInfo.url

                username = dbInfo.userName

                password = dbInfo.password

                properties {
                    initialSize = 4
                    maxActive = 4
                    minEvictableIdleTimeMillis=1800000
                    timeBetweenEvictionRunsMillis=1800000
                    numTestsPerEvictionRun=3
                    testOnBorrow=true
                    testWhileIdle=true
                    testOnReturn=true
                    validationQuery='SELECT 1'
                }
            }
        }
        openlab.database.name = 'cloud mySQL instance'
        grails.serverURL = "http://www.openlabframework.cfapps.io"
    } */
    standalone {
        dataSource {
            pooled = true
            driverClassName = 'org.hsqldb.jdbcDriver'
            url = 'jdbc:hsqldb:file:standaloneDB'
            username = "sa"
            password = ""
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
        }
        openlab.upload.dir = "upload/"
        openlab.database.name = 'HSQLDB SQL instance'
    }

}