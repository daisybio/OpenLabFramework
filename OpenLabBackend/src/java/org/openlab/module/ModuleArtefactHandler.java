package org.openlab.module;

import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;

public class ModuleArtefactHandler extends ArtefactHandlerAdapter {

    // the name for these artefacts in the application
    static public final String TYPE = "Module";
 
    // the suffix of all product handler classes (i.e. how they are identified as product handlers)
    static public final String SUFFIX = "Module";
     
    public ModuleArtefactHandler() {
        super(TYPE, ModuleClass.class, DefaultModuleClass.class, SUFFIX);
    }	
	
}
