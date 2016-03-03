OpenLabFramework
================

OpenLabFramework (OLF) is a groovy & grails based open-source laboratory information management system (LIMS) 
intended for advanced sample management in small to mid-sized laboratories. It has been developed with focus
on the management of vector clone and genetically engineered cell lines. It can, however, be adapted to cover
other biomaterials. Thanks to a modular structure and the use of grails, new functionality can be added with
little developmental effort. Features currently included are among others: 

* Sample management of genes, gene variants, vector clones, and genetically engineered cell-lines,
* together with information about medium composition, antibiotics and other cell culture conditions.
* Physical sample tracking with QR barcodes and direct printing of labels using DYMO label printers.
* Document management where arbitrary files can be uploaded and linked to samples in the database
* Dynamic web-interface with heavy use of java-script and Ajax with the goal of being more responsive,
  intuitive, and interactive.

For more information please have a look at the github WIKI where you can find more information for users, 
administrators, and software developers:

https://github.com/NanoCAN/OpenLabFramework/wiki

A demo is available at http://www.nanocan.dk/openlabframework/demo (user: admin, password: demo0815)

For more information about our center, as well as for contact details: http://www.nanocan.org

__Important note__: The latest version of OpenLabFramework was only tested on grails 2.5.x. For grails < 2.3.x use OLF v.1.3.2. Hint: Use [sdkman](http://gvmtool.net) if you need to conveniently install and use several grails versions in parallel.

Browser compatibility: OLF should work on all modern browsers that support javascript.

Release Notes:
* v. 1.4.1 Upgrade to grails 2.5.3
* v. 1.4.0 Upgrade to grails 2.5.0
* v. 1.3.2 Bugfixes and further improvements following changes in 1.3.1
* v. 1.3.1 Full inclusion of OpenLabNotes and restricted access option for DataObjects
* v. 1.3.0 Code cleanup and bugfixes in storage / attachment plug-ins. new restful web service that allows you to access data in OpenLabFramework with other applications. AppAccessTokens limit access to 
allowed applications only 
* v. 1.2.3 Search functionality is now more efficient. performance was severly degrading for several thousand entries
* v. 1.2.2 Upgrade to grails 2.2.5, upgrade of spring-mobile to 0.5.1, completely restructured code base with git submodules. All plug-ins are from now on dynamically loaded from the subfolder modules.
* v. 1.2.1 Upgrade to grails 2.2.4, smaller bug fixes, code cleaning, wiki updates und enhanced select
* v. 1.2 Several bug fixes
* v. 1.1 First public release



