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
package openlab.attachments

import grails.converters.JSON
import org.apache.commons.collections.Predicate
import org.springframework.util.ClassUtils
import uk.co.desirableobjects.ajaxuploader.AjaxUploaderService
import uk.co.desirableobjects.ajaxuploader.exception.FileUploadException
import javax.servlet.http.HttpServletRequest
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile

class DataObjectAttachmentController {

    def searchableService
    def fileUploadHelperService
    def ajaxUploaderService
    def grailsApplication

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
        [dataObjectAttachmentInstanceList: DataObjectAttachment.list(params), dataObjectAttachmentInstanceTotal: DataObjectAttachment.count(), bodyOnly: false]
    }

    /**
     * Allows to create new file attachment using an addin. Currently supports only one file at a time, but could do with more in theory.
     */

    def uploadFile = {
        //create temporary file
        def timestamp = new java.util.Date().time
        def uploadedTempFile = new File(((grailsApplication.config.openlab?.upload?.dir?:"") + "upload" + timestamp + ".tmp"))

        try{
                if(!new File(grailsApplication.config.openlab?.upload?.dir?:"").canWrite()){
                    return render(text: [success: false] as JSON, contentType: 'text/json')
                }

                ajaxUploaderService.upload((InputStream) fileUploadHelperService.selectInputStream(request), uploadedTempFile)
        } catch(FileUploadException e){
            log.error("Failed to upload file.", e)
            return render(text: [success:false] as JSON, contentType:'text/json')
        }

        return render(text: [success:true, tempFile: uploadedTempFile.path] as JSON, contentType:'text/json')

    }

    def createWithAddin = {

        //create one dataobjectattachment for each file that has been uploaded
        for(int currentFile = 0; currentFile <= params.int("filesUploaded"); currentFile++){
            println params."fileName_${currentFile}"

            //fill a dataobject attachment instance with linked data object
            DataObjectAttachment doaInstance = fileUploadHelperService.createDataObjectAttachmentInstance(params)

            //rename the temporary uploaded file to a permanent file and link it to the doaInstance
            doaInstance = fileUploadHelperService.processTempFile(params."filePath_${currentFile}", params."fileName_${currentFile}", doaInstance)
            if(!doaInstance){
                flash.message = "Failed to process uploaded file"
                render template: "../addins/attachmentsAddin", layout: "body"
                return
            }

            //save it
            else if (!doaInstance.save(flush: true)) {
                flash.message = "Save failed"
                render template: "../addins/attachmentsAddin", layout: "body"
                return
            }
        }

        flash.message = "Save successful"
        render template: "../addins/attachmentsAddin", layout: "body"
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
                        redirect(action: "show", id: params.id)
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

    def deleteWithinTab = {
        println params
        def dataObjectAttachmentInstance = DataObjectAttachment.get(params.id)
        if (dataObjectAttachmentInstance) {
            try {
                File file = new File(dataObjectAttachmentInstance.getPathToFile());

                if (file.exists()) {
                    if (file.canWrite()) {
                        file.delete();
                    }
                    else {
                        render (text: [success: false, message: "Could not write on file!"] as JSON, contentType:'text/json')
                    }

                    dataObjectAttachmentInstance.delete(flush: true)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
                }
                else {
                    dataObjectAttachmentInstance.delete(flush: true)
                }

                render (text: [success: false, message: "File does not exist, deleted only the database entry."] as JSON, contentType:'text/json')
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                def message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
                render (text: [success: false, message: message] as JSON, contentType:'text/json')
            }
            catch (IOException ioe) {
                def message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
                render (text: [success: false, message: message] as JSON , contentType:'text/json')
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment'), params.id])}"
            render (text: [success: true] as JSON, contentType:'text/json')
        }
    }

    def renderAttachmentsTab = {
        def module = new org.openlab.module.tab.AttachmentsTabModule()
        def model = module.getModelForDomainClass("", params.id)
        render template: "../tabs/attachmentsTab", model: model
    }
}
