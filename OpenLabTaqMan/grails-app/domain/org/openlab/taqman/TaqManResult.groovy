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
