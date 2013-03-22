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
