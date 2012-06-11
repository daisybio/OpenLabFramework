package org.openlab.taqman

import openlab.attachments.DataObjectAttachment

class TaqManResultService {

    static transactional = true

    /*
    Extract the rows of a CSV TaqMan file, skipping the first 29 rows
     */
    def getTaqManRows(attachmentId){
        FileInputStream inputFile = getFileStream(false, attachmentId)
        def lines = inputFile.readLines()
        lines = lines.subList(30, lines.size())
        List<String[]> rows = lines.collect { it.split(',')}
        inputFile.close()

        return rows
    }

    /*
    opening a file input or output stream on an dataobject attachment
     */
    def getFileStream(writable, attachmentId)
    {
        def daoInstance = DataObjectAttachment.findById(attachmentId)

        def detectorList = TaqManAssay.list()
        def file = new File(daoInstance.pathToFile)

        if (!writable)
            return new FileInputStream(file)

        else
            return new FileOutputStream(file, false)
    }
}
