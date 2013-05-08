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
