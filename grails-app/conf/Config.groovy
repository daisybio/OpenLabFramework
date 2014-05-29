import org.cloudfoundry.runtime.env.CloudEnvironment
import org.cloudfoundry.runtime.env.RedisServiceInfo
import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.Priority
import org.apache.log4j.ConsoleAppender
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

def olfLogLevel = "ERROR"
def olfLogPattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} [%t] %x %-5p %c{2} - %m%n"
def log4jFileName = System.properties.getProperty('catalina.base', '.') + "/logs/openlabframework.log"


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

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
        //}
        //debug  'org.hibernate.SQL'
        /*'org.hibernate.transaction',
       'org.hibernate.jdbc' */

        appender new DailyRollingFileAppender(name: "olfLog",
                threshold: Priority.toPriority(olfLogLevel),
                file: log4jFileName,
                datePattern: "'.'yyyy-MM-dd",   //Rollover at midnight each day.
                layout: pattern(conversionPattern: olfLogPattern)
        )
        if (grails.util.Environment.current == grails.util.Environment.DEVELOPMENT || grails.util.Environment.current == grails.util.Environment.TEST) {
            appender new ConsoleAppender(name: "console",
                    threshold: Priority.toPriority(olfLogLevel),
                    layout: pattern(conversionPattern: olfLogPattern)
            )
        }
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate',
            'org.nanocan'
    warn   'org.nanocan'
    info   'org.nanocan'
    /*debug   'grails.plugins.springsecurity'
    debug   'org.codehaus.groovy.grails.plugins.springsecurity'
    debug   'org.springframework.security'
    debug   'org.jasig.cas.client'*/
    debug   'org.nanocan',
            'grails.plugins.springsecurity',
            'grails.plugin.springcache',
            'org.codehaus.groovy.grails.plugins.springsecurity',
            'org.apache.http.headers',
            'grails.app.services',
            'grails.app.domain',
            'grails.app.controllers',
            'grails.plugin.databasemigration',
            'liquibase'

    List<String> loggers = []
    loggers.add('olfLog')
    if (grails.util.Environment.current.name == "development" ||
            grails.util.Environment.current.name == "test") {
        loggers.add('console')
    }
    root {
        error loggers as String[]
        additivity = true
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
    select2{
        dependsOn 'jquery'
        resource url: 'js/select2/select2.min.js'
    }
    jquerymobileaddons {
        dependsOn 'jquery-mobile'
        resource url: 'js/jquery.mobile.pagination.js'
        resource url: '/css/jquery.mobile.pagination.css'
        resource url: 'js/jquery.mobile.autocomplete.js'
        resource url: '/js/jquery.tinyscrollbar.min.js'
    }
    overrides{
        fileuploader{
            resource id: '/css/uploader.css', url: '/css/custom_uploader.css'
        }
    }
    tinymce{
		dependsOn 'jquery'
		resource url: 'js/tinymce/tinymce.min.js'
		resource url: 'js/tinymce/themes/modern/theme.js'
		resource url: 'js/tinymce/plugins/table/plugin.min.js'
		resource url: 'js/tinymce/plugins/textcolor/plugin.min.js'
		resource url: 'js/tinymce/plugins/code/plugin.min.js'
		resource url: 'js/tinymce/skins/lightgray/content.min.css'
		resource url: 'js/tinymce/skins/lightgray/skin.min.css'
		
    }
}

//db migration
//grails.plugin.databasemigration.changelogFileName = 'changelog-1.0.groovy'

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

