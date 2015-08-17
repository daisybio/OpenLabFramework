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
grails.plugin.location.prototype = "prototype"

grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)

forkConfig = [maxMemory: 1024, minMemory: 512, debug: false, maxPerm: 256]
grails.project.fork = [
        test: forkConfig, // configure settings for the test-app JVM
        run: forkConfig, // configure settings for the run-app JVM
        war: forkConfig, // configure settings for the run-war JVM
        standalone: forkConfig // configure settings for the Swing console JVM
]

grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
grails.project.war.file = "target/${appName}.war"
//grails.tomcat.jvmArgs= ["-Xms512m",  "-Xmx1024m", "-XX:PermSize=512m", "-XX:MaxPermSize=512m"]
grails.project.dependency.resolver = "maven" // or ivy

//grails.plugin.standalone.tomcatVersion = "7.0.52.1"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
        //excludes "bcpg-jdk15", "bcprov-jdk15"
    }
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://repo.grails.org/grails/repo/"
    }
    dependencies {
        runtime "hsqldb:hsqldb:1.8.0.10"
        runtime 'mysql:mysql-connector-java:5.1.16'
        runtime "net.sourceforge.jtds:jtds:1.3.1" //MS-SQL
        runtime 'org.bouncycastle:bcpg-jdk15on:1.50'
        runtime 'org.bouncycastle:bcprov-jdk15on:1.50'
        runtime ("org.docx4j:docx4j-ImportXHTML:3.2.2"){
            excludes 'sl4j-log4j12'
        }
        runtime "net.sf.jtidy:jtidy:r938"
        //compile "org.quartz-scheduler:quartz:1.7.3"
        compile 'commons-beanutils:commons-beanutils:1.8.3'
    }

    plugins{
        build ':tomcat:7.0.54'
        runtime ':hibernate:3.6.10.18'
        compile ":jquery:1.11.1"
        compile ":jquery-ui:1.10.4"
        runtime ":resources:1.2.14"
        //runtime ":database-migration:1.4.0"
        compile ':scaffolding:2.1.2'

        compile ":searchable:0.6.9"
        compile ":webflow:2.1.0"

        compile ":jquery-mobile:1.1.0.5"
        //compile ":excel-import:1.0.0"
        compile ":spring-security-core:1.2.7.4"
        //compile ":spring-security-acl:1.1.1"
        compile ":spring-security-cas:1.0.5"
        compile ':webxml:1.4.1'
        compile ":spring-mobile:1.1.3"
        compile ":standalone:1.3"
        compile ":quartz:1.0.2"

        compile ":yui:2.8.2.1"
        compile (":grails-ui:1.2.3") {
            excludes 'yui'
        }

        compile ":ajax-uploader:1.1"
        compile ":bubbling:2.1.4"
        compile (":export:1.6"){
            excludes 'bcmail-jdk14', 'bcprov-jdk14', 'bctsp-jdk14'
        }
        compile ":joda-time:1.5"
    }
}
