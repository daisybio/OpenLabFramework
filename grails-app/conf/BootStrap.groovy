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
import grails.util.GrailsUtil
import org.openlab.barcode.BarcodeDataObject
import org.openlab.barcode.BarcodeLabel
import org.openlab.barcode.BarcodeSite
import org.openlab.main.Laboratory
import org.openlab.main.Project
import org.openlab.security.Requestmap
import org.openlab.security.Role
import org.openlab.security.User
import org.openlab.security.UserRole
import org.openlab.storage.Box
import org.openlab.storage.Compartment
import org.openlab.storage.Freezer
import org.openlab.storage.StorageLocation
import org.openlab.genetracker.Recombinant
import org.openlab.genetracker.Gene

class BootStrap {

    //def moduleHandlerService
    //def applicationHandlerService
    //def addinHandlerService
    def settingsService
    def grailsApplication
    def springSecurityService
    def searchableService
    def dataSource

    def init = { servletContext ->

        println "Database name: " + grailsApplication.config.openlab.database.name

        initSettings()
        initUserbase()
        initData()
        initSecurity()

        switch (GrailsUtil.environment) {
            case "development":

                //initDummyData()
                //initSearchablePlugin()

                break

            case "production":

                initSearchablePlugin()

                break

            case "standalone":

                break

        }
    }

    def destroy = {
    }

    def initData() {
        println "Create some initial data if necessary."

        if (BarcodeSite.list().size() == 0)
            def barcodeSite = new BarcodeSite(name: "Odense", repositoryName: "Odense", description: "Odense Repository", siteLetter: "OD").save()
        if (BarcodeDataObject.list().size() == 0) {
            def barcodeDO = new BarcodeDataObject(typeLetter: "GE", defaultText: "", defaultDescription: "", shortName: "gene", fullName: "org.openlab.genetracker.Gene").save()
            new BarcodeDataObject(typeLetter: "CD", defaultText: "", defaultDescription: "", shortName: "cellLineData", fullName: "org.openlab.genetracker.CellLineData").save()
            new BarcodeDataObject(typeLetter: "RE", defaultText: "", defaultDescription: "", shortName: "recombinant", fullName: "org.openlab.genetracker.Recombinant").save()
        }
        if (BarcodeLabel.list().size() == 0)
            def barcodeLabel = new BarcodeLabel(name: "Label", parameters: "", barcodeType: "QR", xml: "").save()

        /*
             * Create data from original geneTracker
             *
            def fileName = "/home/markus/genetrackerData.xls" //"C:\\temp\\genetrackerData.xls"
            def importer = new org.openlab.dataimport.GeneTrackerImporter(fileName)
            importer.createAll()
            //importer.createOnlyMasterData()*/
    }

    def initSearchablePlugin() {
        // We manually start the mirroring process to ensure that it comes after
        // Autobase performs its migrations.
        println "Performing bulk index"
        searchableService.reindex()
        println "Starting mirror service"
        searchableService.startMirroring()
    }

    def initUserbase() {
        /*check if users are in the database */
        def usersGiven = (User.list().size() > 0)

        if (!usersGiven) {
            println "Create default users 'admin' and 'user'..."
            /*Spring Security Config*/
            def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
            def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

            def password= "demo0815"

            def testUser = new User(userRealName: 'Administrator', email: '', username: 'admin', enabled: true, password: password)
            testUser.save(flush: true)

            UserRole.create(testUser, adminRole, true)
            UserRole.create(testUser, userRole, true)

            def anotherUser = new User(userRealName: 'User', username: 'user', email: '', enabled: true, password: password)
            anotherUser.save(flush: true)

            UserRole.create(anotherUser, userRole, true)
        }
    }

    def initSettings() {

        println "Create default settings..."
        /**
         * Default Settings
         */
        if (!settingsService.exists(key: "default.language"))
            settingsService.setDefaultSetting(key: "language", value: "EN")


        def key = "genetracker.ncbi.baseurl"
        if (!settingsService.exists(key: key))
            settingsService.setSetting(key: key, value: "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?")

        key = "genetracker.ncbi.db"
        if (!settingsService.exists(key: key))
            settingsService.setSetting(key: key, value: "nuccore")

        key = "genetracker.ncbi.rettype"
        if (!settingsService.exists(key: key))
            settingsService.setSetting(key: key, value: "gb")

        key = "genetracker.ncbi.retmode"
        if (!settingsService.exists(key: key))
            settingsService.setSetting(key: key, value: "xml")

        key = "genetracker.ncbi.sequenceviewer.url"
        if (!settingsService.exists(key: key))
            settingsService.setSetting(key: key, value: "http://www.ncbi.nlm.nih.gov/projects/sviewer/")

        key = "default.storage.axis.x"
        if (!settingsService.exists(key: key))
            settingsService.setSetting(key: key, value: "alpha")
    }

    def initSecurity() {
        /* create requestmaps to enable url security */
        if (Requestmap.list().size() == 0) {
            def requestMap = new Requestmap(url: "/error/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/*", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/js/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/css/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/images/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/plugins/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/captcha/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/register/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/**", configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY")
            requestMap.save()

            requestMap = new Requestmap(url: "/user/**", configAttribute: "ROLE_ADMIN")
            requestMap.save()

            requestMap = new Requestmap(url: "/application/**", configAttribute: "ROLE_ADMIN")
            requestMap.save()

            requestMap = new Requestmap(url: "/admin/**", configAttribute: "ROLE_ADMIN")
            requestMap.save()

            requestMap = new Requestmap(url: "/dataSource/**", configAttribute: "ROLE_ADMIN")
            requestMap.save()

            requestMap = new Requestmap(url: "/settings/**", configAttribute: "ROLE_ADMIN")
            requestMap.save()
        }
    }

    def initDummyData() {
        println "Create dummy data..."
        def laboratory = new Laboratory(description: "test", name: "odense")
        laboratory.save()

        def project = new Project(description: "test", name: "proj", laboratory: laboratory)
        project.save()

        /* setup storage dummy data */
        def storageLocation = new StorageLocation(zip: "72766", city: "Reutlingen", street: "Spitzaeckerweg", number: "5", floor: "2", room: "123", description: "Kabuff")
        storageLocation.save()

        def freezer = new Freezer(temp: "5", description: "Freezer1", location: storageLocation)
        freezer.save()

        def comp2 = new Compartment(description: "comp2", storageType: freezer)
        comp2.save()

        def comp1 = new Compartment(description: "comp1", storageType: freezer)
        comp1.save()

        def box1 = new Box(xdim: 2, ydim: 3, description: "ProbeBox", compartment: comp2)
        box1.save()

        def box2 = new Box(xdim: 1, ydim: 8, description: "Yet another box", compartment: comp2)
        box2.save()

        def box3 = new Box(xdim: 20, ydim: 20, description: "Huge box", compartment: comp1)
        box3.save()

        def box4 = new Box(xdim: 12, ydim: 8, description: "96 Well", compartment: comp2)
        box4.save()

        comp2.boxes = [box2, box1, box4]
        comp2.save()

        comp1.boxes = [box3]
        comp1.save()

        freezer.compartments = [comp1, comp2]
        freezer.save()

        def freezer2 = new Freezer(temp: "10", description: "Freezer2", location: storageLocation)
        freezer2.save()

        def comp3 = new Compartment(description: "comp3", storageType: freezer2)

        freezer2.compartments = [comp3]
        freezer2.save()
    }
} 