package org.openlab.taqman

import openlab.attachments.DataObjectAttachment
import org.openlab.main.DataObject
import org.openlab.main.MainObject

class TaqManResult extends MainObject implements Serializable  {

    int cycleNumber = 40
    int annealingTemperature = 60
    DataObjectAttachment attachment
    PurificationMethod purificationMethod
    ReverseTranscriptionKit reverseTranscriptionKit
    ReverseTranscriptionPrimer reverseTranscriptionPrimer
    String cDNAtemplate
    boolean detectorsAssigned
    boolean samplesAssigned
    boolean referenceResult

    static belongsTo = DataObjectAttachment
    static hasMany = [dataObjects: DataObject, samples: TaqManSample, detectors: TaqManAssay]

    static constraints = {
        attachment()
        cDNAtemplate(inList: ["10ng", "100ng"])
        purificationMethod()
        reverseTranscriptionPrimer()
        reverseTranscriptionKit()
        annealingTemperature()
        cycleNumber()
        detectorsAssigned nullable: false
        samplesAssigned nullable: false
        referenceResult nullable: false
    }

    static mapping = {
        table 'taqManResult'
        cache true
    }

    String toString() {
        attachment.toString() + "-Protocol"
    }
}
