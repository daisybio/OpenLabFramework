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
package openlabattachments

import javax.servlet.http.HttpServletRequest
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile
import openlab.attachments.DataObjectAttachment

class FileUploadHelperService {

    def grailsApplication

    InputStream selectInputStream(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
            return uploadedFile.inputStream
        }
        return request.inputStream
    }

    DataObjectAttachment createDataObjectAttachmentInstance(def params) {
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

                if (domainInstance)
                    doaInstance.addToDataObjects(domainInstance)
            }
        }
        doaInstance
    }

    DataObjectAttachment processTempFile(tempFile, fileName, doaInstance)
    {
        def f = new File(tempFile)

        if (f.canRead()) {
            def timestamp = new java.util.Date()

            doaInstance.setFileUploadDate(timestamp)

            String folder = grailsApplication.config.openlab?.upload?.dir?: ""
            String newPath = folder + timestamp.getTime().toString() + "_" + fileName
            doaInstance.setFileName(fileName)
            def fileArray = fileName.toString().split("\\.")
            doaInstance.setFileType(fileArray[fileArray.length - 1].toUpperCase())
            doaInstance.setPathToFile(newPath)
            doaInstance.setDescription("")

            f.renameTo(new File(newPath))
            return doaInstance
        }
        else return null
    }
}
