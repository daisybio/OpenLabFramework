// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.tomcat.jvmArgs= ["-Xms256m",  "-Xmx1024m", "-XX:PermSize=512m", "-XX:MaxPermSize=512m"]

/* Search for external config files */
def ENV_NAME = "OPENLABFRAMEWORK_CONFIG"
if (!grails.config.locations || !(grails.config.locations instanceof List)) {
    grails.config.locations = []
}
if (System.getenv(ENV_NAME)) {
    println "Including configuration file specified in environment: " + System.getenv(ENV_NAME);
    grails.config.locations << "file:" + System.getenv(ENV_NAME).replace('\\', '/')
}
else if (System.getProperty(ENV_NAME)) {
    println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
    grails.config.locations << "file:" + System.getProperty(ENV_NAME)
}
else {
    println "Using default settings..."
    dataSource.driverClassName = 'org.hsqldb.jdbcDriver'
    dataSource.url = 'jdbc:hsqldb:file:testDB'
    dataSource.username = 'sa'
    dataSource.password = ''
    dataSource.dbCreate = 'update'
    grails.plugins.springsecurity.cas.active = false
    openlab.database.name = 'fileHSQLDB'
    grails.serverURL = 'http://localhost:8080/OpenLabFramework'
}

/* */
cgrails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text-plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        pdf: 'application/pdf',
        rtf: 'application/rtf',
        excel: 'application/vnd.ms-excel',
        ods: 'application/vnd.oasis.opendocument.spreadsheet',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/dashboard/index'

// set per-environment serverURL stem for creating absolute links
environments {
    production {
    }
    development {
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    cloud {
        dataSource {
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            url = "jdbc:mysql://localhost/olfdb?useUnicode=yes&characterEncoding=UTF-8"
            username = "na"
            password = "na"
            dbCreate = "update"
        }
        openlab.database.name = 'cloud mySQL instance'
        grails.serverURL = "http://openlabframework.cloudfoundry.com"
    }

}
// log4j configuration
log4j = {

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.openlab',
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'org.openlab',
            'org.grails.plugins.excelimport'

    info 'org.codehaus.groovy.grails.web.servlet',
            'org.openlab',
            'org.openlab.dataimport',
            'grails.app'

    appenders {
        /*rollingFile  name:'infoLog', file:'info.log', threshold: org.apache.log4j.Level.INFO, maxFileSize:1024
        rollingFile  name:'warnLog', file:'warn.log', threshold: org.apache.log4j.Level.WARN, maxFileSize:1024
        rollingFile  name:'errorLog', file:'error.log', threshold: org.apache.log4j.Level.ERROR, maxFileSize:1024
        rollingFile  name:'debugLog', file:'debug.log', threshold: org.apache.log4j.Level.DEBUG, maxFileSize:1024*/
        console      name:'stdout', threshold: org.apache.log4j.Level.WARN
    }
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'org.openlab.security.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'org.openlab.security.UserRole'
grails.plugins.springsecurity.authority.className = 'org.openlab.security.Role'
grails.plugins.springsecurity.requestMap.className = 'org.openlab.security.Requestmap'
grails.plugins.springsecurity.securityConfigType = 'Requestmap'

//resource plugin
//grails.resources.debug=true

grails.resources.modules = {
    labelPrinter{
        resource url: '/js/DYMO.Label.Framework.latest.js', disposition: 'head'
    }
    myyui{
        resource url: '/css/yui_fonts_min.css'
        resource url: '/css/yui_override.css'
        resource url: '/css/yui_reset_grid.css'
    }
    prototypeManual{
        resource url: [plugin: "prototype", dir: "js/prototype", file: "prototype.js"], disposition: "head"
        resource url: [plugin: "prototype", dir: "js/prototype", file: "scriptaculous.js"], disposition: "head"
        resource url: [plugin: "prototype", dir:"js/prototype", file: "effects.js"], disposition: "head"
        resource url: [plugin: "prototype", dir:"js/prototype", file: "controls.js"], disposition: "head"
    }
    jstree {
        dependsOn 'jquery'
        resource url: 'js/jquery.jstree.js'
    }
    jquerymobileaddons {
        dependsOn 'jquery-mobile'
        resource url: 'js/jquery.mobile.pagination.js'
        resource url: '/css/jquery.mobile.pagination.css'
        resource url: 'js/jquery.mobile.autocomplete.js'
        resource url: '/js/jquery.tinyscrollbar.min.js'
    }
}

//db migration
grails.plugin.databasemigration.changelogFileName = 'changelog-1.0.groovy'

//searchable plugin interfers with db migration, turn off for now.
searchable {
    mirrorChanges = false
    bulkIndexOnStartup = false
}

//set default id column type to numeric(19,0) otherwise grails 2.0 introduces bigint which leads to FK conflicts
grails.gorm.default.mapping = {
   //id type: Long, sqlType: 'numeric(19,0)'
}

//define default javascript library
grails.views.javascript.library="prototype"



