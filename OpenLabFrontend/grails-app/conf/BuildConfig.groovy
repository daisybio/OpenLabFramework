grails.plugin.location.backend = "../OpenLabBackend"
grails.plugin.location.geneTracker = "../GeneTracker"
grails.plugin.location.storage = "../Storage"
grails.plugin.location.barcode = "../OpenLabBarcode"
grails.plugin.location.attachments = "../OpenLabAttachments"
grails.plugin.location.taqman = "../OpenLabTaqMan"
//grails.plugin.location.documents = "../OpenLabDocuments"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
grails.project.war.file = "target/${appName}.war"
grails.tomcat.jvmArgs= ["-Xms512m",  "-Xmx1024m", "-XX:PermSize=512m", "-XX:MaxPermSize=512m"]
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        compile "org.grails:grails-webflow:$grailsVersion"
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        runtime "hsqldb:hsqldb:1.8.0.10"
        runtime 'mysql:mysql-connector-java:5.1.16'
    }

    plugins{
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"
        build ":tomcat:$grailsVersion"

        compile ":searchable:0.6.4"
        compile(":webflow:2.0.0") {
            exclude 'grails-webflow'
        }

        compile ":excel-import:1.0.0"
        compile ":spring-security-core:1.2.7.3"
        //compile ":spring-security-acl:1.1.1"
        compile ":spring-security-cas:1.0.5"
    }

}

