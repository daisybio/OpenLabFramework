// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

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
    println "No external configuration file defined for ${ENV_NAME}. Using local properties file instead (/WEB-INF/classes/openlabframework.properties)"
    grails.config.locations << "classpath:openlabframework.properties"
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

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://10.84.12.130:8080/${appName}"
    }
    development {
        grails.serverURL = "http://10.84.12.130:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
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

    //all    'jdbc.sqltiming'
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
        dependsOn 'scriptaculous'
        defaultBundle "prototype"
        resource url: [plugin: "prototype", dir:"js/prototype", file: "effects.js"], disposition: "head"
        resource url: [plugin: "prototype", dir:"js/prototype", file: "controls.js"], disposition: "head"
    }
    jstree {
        dependsOn 'jquery'
        resource url: 'js/jquery.jstree.js'
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

//CAS configuration for single sign on
grails.plugins.springsecurity.cas.loginUri = '/login'
grails.plugins.springsecurity.cas.serviceUrl = grails.serverURL + '/j_spring_cas_security_check'
grails.plugins.springsecurity.cas.serverUrlPrefix = 'https://sso.sdu.dk'
//grails.plugins.springsecurity.cas.proxyCallbackUrl = grails.serverURL + '/secure/receptor'
grails.plugins.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'
grails.plugins.springsecurity.logout.afterLogoutUrl = 'https://sso.sdu.dk/logout?url=' + grails.serverURL
