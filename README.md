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

__Important note__: OpenLabFramework has been developed on grails 2.2.x and is only guaranteed to work with this version. Hint: Use [gvmtool](http://gvmtool.net) if you need to conveniently install and use several grails versions in parallel.

Browser compatibility: OLF should work on all modern browsers that support javascript, but has only been tested on 
* Chrome (31.0.1650.63)
* Firefox (26.0)

Release Notes:
* v. 1.2.3 Search functionality is now more efficient. performance was severly degrading for several thousand entries
* v. 1.2.2 Upgrade to grails 2.2.5, upgrade of spring-mobile to 0.5.1, completely restructured code base with git submodules. All plug-ins are from now on dynamically loaded from the subfolder modules.
* v. 1.2.1 Upgrade to grails 2.2.4, smaller bug fixes, code cleaning, wiki updates und enhanced select
* v. 1.2 Several bug fixes
* v. 1.1 First public release



