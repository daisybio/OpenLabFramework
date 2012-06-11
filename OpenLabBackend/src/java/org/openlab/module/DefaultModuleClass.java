package org.openlab.module;

import  org.codehaus.groovy.grails.commons.AbstractGrailsClass;
import org.codehaus.groovy.grails.commons.GrailsClassUtils;

public class DefaultModuleClass extends AbstractGrailsClass implements ModuleClass{
		
    public DefaultModuleClass(Class clazz) {
        super(clazz, ModuleArtefactHandler.SUFFIX);
    }
     
    // This method will get the static property 'key' on the underlying
    // Module class that it represents.
    public String getKey() {
        Object key = GrailsClassUtils.getStaticPropertyValue(getClazz(), "key");
        if (key == null) {
            return null;
        } else {
            return key.toString();
        }
    }	    
}
