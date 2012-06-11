package openlab.attachments

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import grails.converters.JSON
import org.apache.commons.collections.Predicate
import org.springframework.util.ClassUtils

class DataObjectAttachmentController {

    def searchableService
    def attachmentableService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    /**
     * send file attachment to browser as binary stream
     */
    def download = {

        def dataObjectAttachmentInstance = DataObjectAttachment.get(params.id)
        if (!dataObjectAttachmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
            redirect(action: "list")
        }
        else {
            def file = new File(dataObjectAttachmentInstance.getPathToFile())
            if (file.exists()) {
                response.setContentType("application/octet-stream")
                response.setHeader("Content-disposition", "attachment;filename=${dataObjectAttachmentInstance.getFileName()}")

                response.outputStream << file.newInputStream()
            }
            else {
                flash.message = "File does not exist on server. It is suggested to delete the orphand entry"
                redirect(action: "edit", id: params.id)
            }
        }
    }

    /**
     * Makes the description field editable
     */
    def editField = {
        // Retrieve member
        def doaInstance = DataObjectAttachment.get(params.id)
        if (doaInstance) {
            doaInstance.properties = params
            doaInstance.save()
            // Render a new page.
            def type = params.editorId
            def strArray = type.split("_")

            if (strArray[0] == "sequence")
                render g.textArea(name: "sequence", value: params.get(strArray[0]).encodeAsHTML(), cols: 80)

            else render params.get(strArray[0]).encodeAsHTML()
        }
    }


    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [dataObjectAttachmentInstanceList: DataObjectAttachment.list(params), dataObjectAttachmentInstanceTotal: DataObjectAttachment.count()]
    }

    /**
     * Allows to create new file attachment using an addin. Currently supports only one file at a time, but could do with more in theory.
     */
    def createWithAddin = {

        def doaInstance = new DataObjectAttachment();

        //extract attachmentLink_* from params. it contains necessary information about which dataObject to attach to.
        for (String key in params.keySet()) {
            if (key.startsWith("attachmentLink_")) {

                def keySplitArray = key.split("_")
                def geneAndId = keySplitArray[1]

                def geneAndIdSplitArray = geneAndId.split(":")
                def id = Long.valueOf(geneAndIdSplitArray[1])
                def domainName = geneAndIdSplitArray[0]

                def splitArray = params[key].split(":")

                //def domainName = splitArray[0]
                def name = splitArray[1]

                def domainInstance = grailsApplication.getArtefactByLogicalPropertyName("Domain", domainName.toString())?.getClazz()?.get(id)

                /*if(domainInstance == null)
                    {
                         render(controller:'dataObjectAttachment', action: 'edit', id: doaInstance.id)
                    }*/

                if (domainInstance)
                    doaInstance.addToDataObjects(domainInstance)
            }
        }

        //get files from request
        def fileIterator = request.getFileNames()

        //create attachments for these files using the dataObjects extracted above.
        while (fileIterator.hasNext()) {
            def f = request.getFile(fileIterator.next())

            if (!f.empty) {
                def timestamp = new java.util.Date()

                doaInstance.setFileUploadDate(timestamp)

                String folder = CH?.config?.openlab?.upload?.dir ?: ""
                String newPath = folder + timestamp.getTime().toString() + "_" + f.getOriginalFilename()
                doaInstance.setFileName(f.getOriginalFilename())
                def fileArray = f.getOriginalFilename().split("\\.")
                doaInstance.setFileType(fileArray[fileArray.length - 1].toUpperCase())
                doaInstance.setPathToFile(newPath)
                doaInstance.setDescription("")

                f.transferTo(new File(newPath))
            }
        }

        if (!doaInstance.save(flush: true)) {
            flash.message = "Upload failed"
            redirect(controller: 'dataObjectAttachment', action: 'edit', id: doaInstance.id)
        }

        flash.message = "Upload successful!"

        if (params.taqMan) {
            redirect(controller: 'taqManResult', action: 'create', params: ['attachment.id': doaInstance.id])
        }

        else {
            redirect(controller: 'dataObjectAttachment', action: 'edit', id: doaInstance.id)
        }
    }

    /**
     * In order to facilitate a possibility of adding dataObjects easily, an ajax auto-complete search field has been added
     * This method delivers the search results
     */
    def searchResultsAsJSON = {

        //first try exact search
        def resultMap = searchableService.search(params.query, params)

        //if no results try * operator
        if (resultMap.total == 0) {
            resultMap = searchableService.search(params.query.toString() + "*", params)
        }

        def filteredResultMap = org.apache.commons.collections.CollectionUtils.select(resultMap.results, new Predicate() {
            public boolean evaluate(final Object dto) {
                return (dto.getClass().superclass == org.openlab.main.DataObject.class);
            }
        });

        def jsonList = filteredResultMap.collect {
            def className = ClassUtils.getShortName(it.getClass())
            def controllerName = className[0].toLowerCase() + className[1..-1]
            [id: (controllerName + ":" + it.id), label: (className + ":" + it.toString())]
        }

        def jsonResult = [
                results: jsonList
        ]
        render jsonResult as JSON
    }

    def create = {
        def dataObjectAttachmentInstance = new DataObjectAttachment()
        dataObjectAttachmentInstance.properties = params
        return [dataObjectAttachmentInstance: dataObjectAttachmentInstance]
    }

    def save = {
        def dataObjectAttachmentInstance = new DataObjectAttachment(params)
        if (dataObjectAttachmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), dataObjectAttachmentInstance.id])}"
            redirect(action: "show", id: dataObjectAttachmentInstance.id)
        }
        else {
            render(view: "create", model: [dataObjectAttachmentInstance: dataObjectAttachmentInstance])
        }
    }

    def show = {
        def dataObjectAttachmentInstance = DataObjectAttachment.get(params.id)
        if (!dataObjectAttachmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [dataObjectAttachmentInstance: dataObjectAttachmentInstance]
        }
    }

    def edit = {
        def dataObjectAttachmentInstance = DataObjectAttachment.get(params.id)
        if (!dataObjectAttachmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [dataObjectAttachmentInstance: dataObjectAttachmentInstance]
        }
    }

    def update = {
        def dataObjectAttachmentInstance = DataObjectAttachment.get(params.id)
        if (dataObjectAttachmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (dataObjectAttachmentInstance.version > version) {

                    dataObjectAttachmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')] as Object[], "Another user has updated this DataObjectAttachment while you were editing")
                    render(view: "edit", model: [dataObjectAttachmentInstance: dataObjectAttachmentInstance])
                    return
                }
            }
            dataObjectAttachmentInstance.properties = params
            if (!dataObjectAttachmentInstance.hasErrors() && dataObjectAttachmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), dataObjectAttachmentInstance.id])}"
                redirect(action: "show", id: dataObjectAttachmentInstance.id)
            }
            else {
                render(view: "edit", model: [dataObjectAttachmentInstance: dataObjectAttachmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def dataObjectAttachmentInstance = DataObjectAttachment.get(params.id)
        if (dataObjectAttachmentInstance) {
            try {
                File file = new File(dataObjectAttachmentInstance.getPathToFile());

                if (file.exists()) {
                    if (file.canWrite()) {
                        file.delete();
                    }
                    else {
                        flash.message = "Could not write on file!"
                        redirect(action: "list")
                    }

                    dataObjectAttachmentInstance.delete(flush: true)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
                }
                else {
                    dataObjectAttachmentInstance.delete(flush: true)
                    flash.message = "File does not exist, deleted only the database entry."
                }

                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
                redirect(action: "list")
            }
            catch (IOException ioe) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
                redirect(action: "list")
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
            redirect(action: "list")
        }
    }
}
