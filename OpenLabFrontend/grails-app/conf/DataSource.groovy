/**dataSource {pooled=false
 driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
 //    driverClassName = "com.p6spy.engine.spy.P6SpyDriver" // use this driver to enable p6spy logging
 username="olab"
 password="keins"}*/

dataSource {
    pooled = false
    //driverClassName = "org.hsqldb.jdbcDriver"
    driverClassName = "com.p6spy.engine.spy.P6SpyDriver" // use this driver to enable p6spy logging
    username = "sa"
    password = ""
}

hibernate {
    //flush.mode = 'commit'
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            //url = "jdbc:sqlserver://172.16.6.250:1433;databaseName=olab;"
            //url= "jdbc:sqlserver://192.168.0.2:1433;databaseName=olabDB;"
            url = "jdbc:hsqldb:mem:devDB"
        }
    }
    test {
        dataSource {
            dbCreate = "create"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "create"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}
