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
//load OpenLabFramework modules dynamically from modules folder
def pluginDir = new File('modules')
pluginDir.eachDir {
  grails.plugin.location."$it.name" = it.path
}
//grails.plugin.location.backend = "../OpenLabBackend"
//grails.plugin.location.geneTracker = "../GeneTracker"
//grails.plugin.location.storage = "../Storage"
//grails.plugin.location.barcode = "../OpenLabBarcode"
//grails.plugin.location.attachments = "../OpenLabAttachments"
//grails.plugin.location.taqman = "../OpenLabTaqMan"
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
    repositories {                    l
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://maven.springframework.org/milestone/"

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
        //compile "org.cloudfoundry:cloudfoundry-runtime:0.8.4"
    }

    plugins{
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"
        //runtime ":database-migration:1.3.2"
        build ":tomcat:$grailsVersion"

        compile ":searchable:0.6.4"
        compile(":webflow:2.0.0") {
            exclude 'grails-webflow'
        }

        compile ":jquery-mobile:1.1.0.5"
        compile ":excel-import:1.0.0"
        compile ":spring-security-core:1.2.7.3"
        //compile ":spring-security-acl:1.1.1"
        compile ":spring-security-cas:1.0.5"
        compile ':webxml:1.4.1'
        compile ":spring-mobile:0.5.1"
        compile ":standalone:1.2.3"

        compile ":yui:2.8.2.1"
        compile (":grails-ui:1.2.3") {
            excludes 'yui'
        }
        compile (":richui:0.8"){
            excludes 'yui'
        }
    }

}

