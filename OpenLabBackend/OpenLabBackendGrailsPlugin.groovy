import org.openlab.module.*;

class OpenLabBackendGrailsPlugin {
    // the plugin version
    def version = "1.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0.4 > *"
    
	/** @author markus.list
	 *  Addition to make modules grails artefacts that are dynamically recognized 
	 *  and loaded
	 */
	
	// register artefact handler
	def artefacts = [ModuleArtefactHandler]
	
	// watch for changes
	def watchedResources = [
		"file:./grails-app/modules/**/*Module.groovy",
		"file:../plugins/*/modules/**/*Module.groovy"	
	]
	
	// swap old class with new one when source defined above changes
	def onChange = { event ->
		if (application.isArtefactOfType(ModuleArtefactHandler.TYPE, event.source)) {
			def oldClass = application.getModuleClass(event.source.name)
			application.addArtefact(ModuleArtefactHandler.TYPE, event.source)
 
			// Reload subclasses
			application.moduleClasses.each {
				if (it.clazz != event.source && oldClass.clazz.isAssignableFrom(it.clazz)) {
					def newClass = application.classLoader.reloadClass(it.clazz.name)
					application.addArtefact(ModuleArtefactHandler.TYPE, newClass)
				}
			}
		}
	}
	
	/**
	 * end insertion
	 */
	
	// the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Your name"
    def authorEmail = ""
    def title = "Plugin summary/headline"
    def description = '''\\
Brief description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/open-lab-backend"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
		/*SpringSecurity.securityConfig.userLookup.userDomainClassName = 'org.openlab.security.User'
		SpringSecurity.securityConfig.userLookup.authorityJoinClassName = 'org.openlab.security.UserRole'
		SpringSecurity.securityConfig.authority.className = 'org.openlab.security.Role'
		SpringSecurity.securityConfig.requestMap.className = 'org.openlab.security.Requestmap'
		SpringSecurity.securityConfig.securityConfigType = grails.plugins.springsecurity.SecurityConfigType.Requestmap*/
    }

    //def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    //}

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
