<%=packageName ? "package ${packageName}\n\n" : ''%>class ${className}Controller {
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def filterService = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("filterService")
	def exportService = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("exportService")
	
    def index = {
        redirect(action: "list", params: params)
    }
	
	def list = { 
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		if(params?.format && params.format != "html")
			export(response: response, extension: params.extension, format: params.format, exportList: ${className}.list())
				
		[${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count(), filterParams: com.zeddware.grails.plugins.filterpane.FilterUtils.extractFilterParams(params)]
	}
	
	def export = {attrs ->
		
		def response = attrs.response
		
		def excluded = grails.persistence.Event.allEvents.toList() + ["dirtyPropertyNames", "dirty", "mapping", "lastModifierId", "hasMany", "class", "belongsTo", "constraints", "searchable", "attached", "errors", "metaClass", "log", "object", "version", "beforeInsert", "beforeUpdate", "mappedBy", "springSecurityService", "type", "typeLabel"]
		List fields = ${className}.newInstance().properties.keySet().toList().findAll { !excluded.contains(it) && !isId(it)}

		response.contentType = org.codehaus.groovy.grails.commons.ConfigurationHolder.config.grails.mime.types[attrs.format]
		response.setHeader("Content-disposition", "attachment; filename=${className}.\${attrs.extension}")
		
		exportService.export(attrs.format, response.outputStream, attrs.exportList, fields, [:], [:], [:])
	}
	
	def isId(def label)
	{
		label.reverse().startsWith("dI")
	}
	
	def filter = {		
		if(!params.max) params.max = 10
		//if(!params.sort) params.sort = ""
		def exportFilterParams = new HashMap(params)
		exportFilterParams.remove("max")
		exportFilterParams.remove("offset")

		if(params?.format && params.format != "html" && com.zeddware.grails.plugins.filterpane.FilterUtils.isFilterApplied(params))
		{
			def filterParams = com.zeddware.grails.plugins.filterpane.FilterUtils.extractFilterParams(params)

			def exportList = filterService.filter( exportFilterParams, ${className} )

			export(response: response, extension: params.extension, format: params.format, exportList: exportList)
		}
		
		render( view:'list',
			model:[ ${propertyName}List: filterService.filter( params, ${className} ),
			${propertyName}Total: filterService.count( params, ${className} ),
			filterParams: com.zeddware.grails.plugins.filterpane.FilterUtils.extractFilterParams(params),
			params:params ] )
	}

    def create = {
    	def ${propertyName} = new ${className}()
        ${propertyName}.properties = params
        return [${propertyName}: ${propertyName}]
    }

    //def authenticateService = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext.getBean("authenticateService")
    
    def getCurrentUser()
    {
        /*def principal = authenticateService.principal()
        def userName = principal.getUsername()
        def dbUser = org.openlab.security.DBUser.matchUser(userName)
        if(dbUser) return dbUser[0]
        else return null*/
    }
    
    def save = {  	
		def ${propertyName} = new ${className}(params)
        if (${propertyName}.save(flush: true)) {
            flash.message = "\${message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
            redirect(action: "show", id: ${propertyName}.id, params: params)
        }
        else {
        	params.bodyOnly = true
			render(view: "create", model: [${propertyName}: ${propertyName}])
        }
    }

    def show = {
		def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list", params: params)
        }
        else {
            [${propertyName}: ${propertyName}]
        }
    }
    
    def createAdditionalContent = {
        	def ${propertyName} = new ${className}()
            ${propertyName}.properties = params
            return [${propertyName}: ${propertyName}]
    }
    
    def tabs = {
      	show()
    }
    
    def additionalBoxes = {
		show()
    }
    
    def mainView = {
		show()
    }

    def edit = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list", params: params)
        }
        else {
            return [${propertyName}: ${propertyName}]
        }
    }

    def update = {   	
    	def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            if (params.version) {
                def version = params.version.toLong()
                if (${propertyName}.version > version) {
                    <% def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
                    ${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[], "Another user has updated this ${className} while you were editing")
                    params.bodyOnly = true
					render(view: "edit", model: [${propertyName}: ${propertyName}], params: [bodyOnly:true])
                    return
                }
            }
            ${propertyName}.properties = params
            if (!${propertyName}.hasErrors() && ${propertyName}.save(flush: true)) {
                flash.message = "\${message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
				params.bodyOnly = true
                redirect(action: "show", id: ${propertyName}.id, params: [bodyOnly:true])
            }
            else {
                render(view: "edit", model: [${propertyName}: ${propertyName}], params: [bodyOnly:true])
            }
        }
        else {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def ${propertyName} = ${className}.get(params.id)
		
		try
		{
			${propertyName}.projects.each{
				it.removeFromObject(${propertyName}).save()
			}
		} catch(groovy.lang.MissingPropertyException mpe)
        {
            log.info "Deleting ${propertyName}: Does not have any projects"
        }
		
        if (${propertyName}) {
            try {
                ${propertyName}.delete(flush: true)
                flash.message = "\${message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
                redirect(action: "list", params: params)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "\${message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
                redirect(action: "show", id: params.id, params: params)
            }
        }
        else {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"

            redirect(action: "list", params: params)
        }
    }
    
    //TODO Think of a better solution than splitting '_' to get rid of id (additional param?)
    
    /** This method is called upon in-line editing of fields. This can either be a
     *  field displayed in mainView (aka show.gsp) or list view (list.gsp). As editor elements
     *  in list.gsp have to be distinguished by an id, we have to split the id by '_' to get rid
     *  of this.
     *  
     *  The method updates the domain object that has been modified, saves the new state and renders
     *  the new value back to the page.
     */
    def editField = {
		// Retrieve member
		def ${propertyName} = ${className}.get(params.id)
		if(${propertyName})
		{
    		${propertyName}.properties = params
    		${propertyName}.save()
    		// Render a new page.
    		def type = params.editorId
    		def strArray = type.split("_")
			
			if(strArray[0] == "sequence")
				 render g.textArea(name:"sequence", value: params.get(strArray[0]).encodeAsHTML(), cols: 80)
			
			else render params.get(strArray[0]).encodeAsHTML()
		}
    }
	
	def editCollectionField = {
		def ${propertyName} = ${className}.get(params.id)
		if(${propertyName})
		{
			def classAndType = params.editorId
			def strArray = classAndType.split("_")
			def refClassName = strArray[0]
			def type = strArray[1]
			long objId = Long.valueOf(params."\${type}")
			def object = grailsApplication.getDomainClass(refClassName).newInstance().get(objId)
			
			${propertyName}."\${type}" = object
			${propertyName}.save()
			
			render g.hiddenField(name:"id", value:object.id) + object.toString()
		}
	}
	
	/**
	 * render modalbox to edit m:n associations 
	 */
	def editMany = {
		def property = params.property
		def ${propertyName} = ${className}.get(params.id)
		
		def listAll = grailsApplication.getDomainClass(params.className).newInstance().list()
		def selected = ${propertyName}."\${property}"
		
		def controllerName = params.controllerName[0].toLowerCase()+params.controllerName.substring(1)
		[all: listAll, selected: selected, propertyName: property, className: params.className, controllerName: controllerName]
	}
	
	/**
	 * called when editMany has been submitted. attention: works only if owning class uses "object" to map child class.
	 */
	def changeProperty = {
		
		def ${propertyName} = ${className}.get(params.id)
		
        def property = params.propertyName
		def targetInstance = grailsApplication.getDomainClass(params.className).newInstance()
		def listAll = targetInstance.list()
		listAll.each{item ->
			if(params."\${item.id}" == 'on' && !(${propertyName}."\${property}".contains(item)))
			{
				item.addToObject(${propertyName}).save(flush:true)
			}
			else if(params."\${item.id}" != 'on' && (${propertyName}."\${property}".contains(item)))
			{
				item.removeFromObject(${propertyName}).save(flush:true)	
			}
			
			if(item.hasErrors())
			{
				item.errors.each{
					log.error it.toString()
				}
			}
		}
		params.bodyOnly = true
		redirect(action: show, params: params)
	}
	
	def updateLink = {
		println "updateLink:"+params
		
		def ${propertyName} = ${className}.get(params.id)
		
		def id = ${propertyName}."\${params.paramName}".id
		
		render g.remoteLink(action: "show", controller: params.refController, id: id, update: "body", params: [bodyOnly:true]){"Show"}
	}
}
