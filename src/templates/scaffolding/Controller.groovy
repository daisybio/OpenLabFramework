<%=packageName ? "package ${packageName}\n\n" : ''%>

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

@Transactional(readOnly = true)
class ${className}Controller {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def exportService
    def grailsApplication

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {

        params.max = Math.min(params.max?params.int('max'):10, 100)
        def ${propertyName}List
        def ${propertyName}Total
        def ${propertyName}Criteria

        def exportParams = params.clone()
        exportParams.offset = 0
        exportParams.max = ${className}.count()

        if(params.Filter == "Filter"){
            ${propertyName}List = ${className}.list()
            if(!params.offset) params.offset = 0

            ${propertyName}Criteria = ${className}.createCriteria()

            ${propertyName}List = ${propertyName}Criteria.list(max: params.format?${className}.count():params.max, offset: params.format?0:params.offset) {
                if(params.creatorFilter) creator{
                    eq('username', params.creatorFilter)
                }
                if(params.lastModifierFilter) lastModifier{
                    eq('username', params.lastModifierFilter)
                }
                if(params.projectFilter) projects{
                    eq('name', params.projectFilter)
                }
                if(params.sort) order(params.sort, params.order)
            }
            ${propertyName}Total = ${propertyName}List.totalCount
        }
        else{
            ${propertyName}List = ${className}.list(params)
            ${propertyName}Total = ${className}.count()
        }

        if(params?.format && params.format != "html"){
            ${propertyName}List = ${className}.list(exportParams)
            ${propertyName}Total = ${className}.count()

            response.contentType = grails.util.Holders.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=${propertyName}.\${params.extension}")

            def notallowed = ["dbName", "springSecurityService", "typeLabel", "beforeInsert", "beforeUpdate", "methods"]
            def fields = new DefaultGrailsDomainClass(${className}.class).persistentProperties.findAll{ !notallowed.contains(it.name) }.collect{it.name}

            exportService.export(params.format, response.outputStream, ${propertyName}List, fields?:[], [:], [:], [:])
        }

        [${propertyName}List: ${propertyName}List, ${propertyName}Total: ${propertyName}Total?:0, params: params]
    }


    def show(${className} ${propertyName}) {
        //def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "Just click on a property to change it."
            //flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])
            redirect(action: "list")
            return
        }

        [${propertyName}: ${propertyName}]
    }

    def create() {
        respond new ${className}(params)
    }

    @Transactional
    def save(${className} ${propertyName}) {
        if (${propertyName} == null) {
            notFound()
            return
        }

        if (${propertyName}.hasErrors()) {
            respond ${propertyName}.errors, view:'create'
            return
        }

        ${propertyName}.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
                redirect ${propertyName}
            }
            '*' { respond ${propertyName}, [status: CREATED] }
        }
    }

    def edit(${className} ${propertyName}) {
        respond ${propertyName}
    }

    @Transactional
    def update(${className} ${propertyName}) {
        if (${propertyName} == null) {
            notFound()
            return
        }

        if (${propertyName}.hasErrors()) {
            respond ${propertyName}.errors, view:'edit'
            return
        }

        ${propertyName}.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: '${className}.label', default: '${className}'), ${propertyName}.id])
                redirect ${propertyName}
            }
            '*'{ respond ${propertyName}, [status: OK] }
        }
    }

    @Transactional
    def delete(${className} ${propertyName}) {

        if (${propertyName} == null) {
            notFound()
            return
        }

        ${propertyName}.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: '${className}.label', default: '${className}'), ${propertyName}.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
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
    def editField(){
        // Retrieve member
        def ${propertyName} = ${className}.get(params.id)
        if(${propertyName})
        {
            ${propertyName}.properties = params
            if(${propertyName}.save(flush:  true))
            {
                // Render a new page.
                def type = params.editorId
                def strArray = type.split("_")

                if(strArray[0] == "sequence")
                    render g.textArea(name:"sequence", value: params.get(strArray[0]).encodeAsHTML(), cols: 80)

                else render params.get(strArray[0]).encodeAsHTML()
            }
            else render("Save failed!")
        }
    }

    def editBoolean(){
        def ${propertyName} = ${className}.get(params.id)
        if(${propertyName})
        {
            ${propertyName}.properties[params.property] = !${propertyName}.properties[params.property]
            if(${propertyName}.save(flush:true))
            {
                render "ok"
            }
            else response.sendError(404)
        }
        else response.sendError(404)
    }

    def editInList(){
        def ${propertyName} = ${className}.get(params.id)
        if(${propertyName})
        {
            ${propertyName}.properties = params
            if(${propertyName}.save(flush:true))
            {
                render "ok"
            }
            else response.sendError(404)
        }
        else response.sendError(404)
    }


    def editCollectionField(){
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

    def addManyToMany(){

        def project = grailsApplication.getDomainClass(params.referencedClassName).newInstance().get(params.selectAddTo)
        def dataObj = ${className}.get(params.id)

        if(!project.addToObject(dataObj).save(flush:true))
            flash.message = "Could not add \${dataObj} to \${project}."

        redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
    }

    def removeManyToMany(){

        def project = grailsApplication.getDomainClass(params.referencedClassName).newInstance().get(params.associatedId)
        def dataObj = ${className}.get(params.id)
        def isOk

        if(dataObj && project)isOk = project.removeFromObject(dataObj).save(flush:true)

        if(!isOk) flash.message = "Could not remove \${dataObj} from \${project}."

        redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
    }


    def addOneToMany(){

        println params
        def associatedObj = grailsApplication.getDomainClass(params.referencedClassName).newInstance().get(params.selectAddTo)
        def dataObj = ${className}.get(params.id)
        dataObj.getProperty(params.propertyName).add(associatedObj)
        dataObj.save(flush:true)

        redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
    }

    def removeOneToMany(){

        def dataObj = ${className}.get(params.id)
        def associatedObj = grailsApplication.getDomainClass(params.referencedClassName).newInstance().get(params.associatedId)
        dataObj.getProperty(params.propertyName).remove(associatedObj)
        dataObj.save(flush:true)

        redirect(action: "show", id: params.id, params: [bodyOnly: params.bodyOnly?:false])
    }

    /**
     * render modalbox to edit m:n associations
     */
    def editMany(){
        def property = params.property
        def ${propertyName} = ${className}.get(params.id)

        def listAll = grailsApplication.getDomainClass(params.className).newInstance().list().sort{it.toString()}
        def selected = ${propertyName}."\${property}"

        def controllerName = params.controllerName[0].toLowerCase()+params.controllerName.substring(1)
        [all: listAll, selected: selected, propertyName: property, className: params.className, controllerName: controllerName]
    }

    /**
     * called when editMany has been submitted. attention: works only if owning class uses "object" to map child class.
     */
    def changeProperty(){

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

    def updateEditable(){
        def currentValue = ${className}.get(params.id)?."\${params.propertyName}"
        def domainClassList = grailsApplication.getDomainClass(params.referencedClassName).newInstance().list().sort{it.toString()}

        render(template: "/layouts/editCollection", model: [currentValue: currentValue, targetController: params.controllerName, targetUpdate: params.propertyName + "Editable", propertyName: params.propertyName,
                                                            thisClassName: params.thisClassName, referencedClassName: params.referencedClassName, id: params.id, domainClassList: domainClassList])
    }

    def saveEditable(){
        def dcInstance = ${className}.get(params.id)
        def rcInstance = grailsApplication.getDomainClass(params.referencedClassName).newInstance().get(params.selected)

        dcInstance."\${params.propertyName}" = rcInstance
        dcInstance.save(flush:true, failOnError: true)

        render template:  "/layouts/showCollection", model: [referencedClassName: params.referencedClassName,
                                                             id: params.id, propertyName: params.propertyName, newValue: rcInstance]

    }

    def updateLink(){

        def ${propertyName} = ${className}.get(params.id)

        def id = ${propertyName}."\${params.paramName}".id

        render g.remoteLink(action: "show", controller: params.refController, id: id, update: "body", params: [bodyOnly:true]){"Show"}
    }

}
