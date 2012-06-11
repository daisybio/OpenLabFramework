package openlab.attachments

import org.openlab.main.DataObject
import org.openlab.main.MainObject

class DataObjectAttachment implements Serializable {

    String pathToFile
    String fileName
    String fileType
    String description
    Date fileUploadDate

    static hasMany = [dataObjects: DataObject]

    static constraints = {
    }

    static mapping = {
        table 'olfAttachment'
        cache true
    }

    String toString() {
        fileName
    }
}
