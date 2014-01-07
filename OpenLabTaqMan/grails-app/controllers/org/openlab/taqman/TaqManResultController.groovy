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

import org.apache.commons.lang.StringUtils
import org.openlab.genetracker.CellLineData
import org.apache.commons.collections.Predicate
import grails.converters.JSON

class TaqManResultController {

    //column that contains the TaqManAssay information
    private static final int DETECTOR = 4
    private static final int SAMPLE = 3
    public static final List sampleTypes = ["water", "sample", "other"]

    static scaffold = TaqManResult

    def taqManResultService
    def searchableService

    /**
     * overwrite save method because we want to add it to a set afterwards.
     */
    def save = {

        def taqManResultInstance = new TaqManResult(params)
        
        if (taqManResultInstance.save(flush: true)) 
        {
            flash.message = "TaqManResult successfully saved!"
            def newParams = [:]
            newParams["taqManResultId"] = taqManResultInstance.id
            newParams["attachment.id"] = taqManResultInstance.attachment.id

            if (!taqManResultInstance.detectorsAssigned) redirect(action: "checkDetectors", params: newParams)
            else if (!taqManResultInstance.samplesAssigned) redirect(action: "assignSamples", params: newParams)
            else redirect(action: session.backAction, controller: session.backController, id: session.backId)
        }
        else {
            render(view: "create", model: [taqManResultInstance: taqManResultInstance])
        }
    }

    def show = {

        def taqManResultInstance = TaqManResult.get(params.id)


        def taqManSets = TaqManSet.withCriteria{
            taqManResults
            {
                'in' ("id", taqManResultInstance?.id)
            }
        }

        [taqManSets:  taqManSets, taqManResultInstance : taqManResultInstance]
    }
    
    def showSampleSelection = {
        [sampleName: params.sampleName, inducers: Inducer.list()]
    }

    def searchCellLineDataAsJSON  = {
        //first try exact search
        def resultMap = searchableService.search(params.query, params)

        //if no results try * operator
        if (resultMap.total == 0)
        {
            resultMap = searchableService.search(params.query.toString() + "*", params)
        }

        def filteredResultMap = org.apache.commons.collections.CollectionUtils.select(resultMap.results,new Predicate(){
            public boolean evaluate(final Object dto) {
                return (dto.getClass() == org.openlab.genetracker.CellLineData.class);
            }
        });

        def jsonList = filteredResultMap.collect{

            [id: it.id, label: it.toString()]
        }

        def jsonResult = [
                results: jsonList
        ]
        render jsonResult as JSON
    }

    /**
     * Assign each sample in the file to a sample in the database
     * This allows for grouping them in the final results
     */
    def assignSamples = {

        def taqManResultInstance = TaqManResult.get(params.taqManResultId)

        def rows = taqManResultService.getTaqManRows(params['attachment.id'])

        def uniqueSet = new HashSet()
        
        rows.each{
            try{
                uniqueSet.add(it[SAMPLE])
            } catch(java.lang.ArrayIndexOutOfBoundsException e){

            }
        }

        [taqManResultInstance: taqManResultInstance, assignedSamples: TaqManSample.findAllByTaqManResult(taqManResultInstance), sampleTypes: sampleTypes, samples: uniqueSet.toList()]
    }

    /**
     * persist sample assignment in database
     */
    private static final String inducerLabel = "inducer_"
    private static final String assignedSampleLabel = "assignedSample_"
    private static final String typeLabel = "type_"
    
    def saveSampleAssignment = {

        def taqManResultInstance = TaqManResult.get(params.taqManResultId)

        def types = params.findAll{it.key.toString().startsWith(typeLabel)}
        def sampleNames = types.keySet().collect{it.toString().substring(5)}

        def incompleteSamples = []

        for (def sampleName in sampleNames)
        {
            def sType = getSampleType(sampleName)
            def sCellLineData

            if(sampleName.size() > 0)
                sCellLineData = getSampleValue(sampleName)
            def sInducer = getInducer(sampleName)
            
            if(sType.equals("sample") && !sCellLineData)
            {
                incompleteSamples.add(sampleName)
            }
            else{
                def sampleExists = taqManResultInstance.samples.find{it.sampleName == sampleName}

                if (sampleExists)
                {
                   sampleExists.cellLineData = sCellLineData
                   sampleExists.sampleType = sType 
                   sampleExists.inducer = sInducer
                   sampleExists.save(flush:true)
                }
                else
                {
                    taqManResultInstance.addToSamples(new TaqManSample(sampleName:  sampleName, sampleType: sType,
                        cellLineData: sCellLineData,
                        inducer: sInducer))
                }
            }
        }

        if (incompleteSamples.size() > 0)
        {
            flash.message = "All samples need to be correctly assigned!"
            render(view: "assignSamples", model: [taqManResultInstance: taqManResultInstance, sampleTypes: sampleTypes, samples: incompleteSamples])
        }
        else{
            taqManResultInstance.samplesAssigned = true
            taqManResultInstance.save(flush:true)
            redirect(action: "addToSet", id: taqManResultInstance.id)
        }
    }

    /**
     * Getter methods for inducer, sample and sampleType
     * @param sample
     * @return
     */
    private def getInducer(def sample)
    {
        def key = inducerLabel + sample.toString().replace(" ", "_")
        def inducerId = params.get(key)

        if (inducerId == "null") return null
        else return Inducer.get(inducerId)
    }
    
    private def getSampleType(def sample)
    {
        return params.get(typeLabel + sample)
    }
    
    private def getSampleValue(def sample)
    {
        def key = assignedSampleLabel + sample.toString().replace(" ", "_") + "_id"

        if (params.get(key) == "")  return null
        else return CellLineData.get(params.get(key) as Long)
    }


    /**
     * "Live" creation of a detector for assigning them in TaqManResults
     */
    def addDetector = {
        
        if (!new TaqManAssay(name: params.name).save(flush: true)  )
             flash.message = "Could not add this detector, maybe it already exists?"

        redirect(action: "checkDetectors", params: params)
    }

    /**
     * After solving the detector issues we want to add the result to a set of results.
     */
    def addToSet = {
        def taqManResultInstance = TaqManResult.get(params.id)

        if (!taqManResultInstance) {
            flash.message = "TaqManResult was null."
            redirect(action: "list", params: params)
        }
        else {
            [taqManResultInstance: taqManResultInstance, taqManSets: TaqManSet.list(), referenceResults: TaqManResult.findAllByReferenceResult(true)]
        }
    }

    /**
     * triggered by addToSet.gsp if TaqManResult is supposed to be added to an existing TaqManSet.
     */
    def saveToSet = {
        println params

        def taqManSetInstance = TaqManSet.get(params.setSelect)
        taqManSetInstance.addToTaqManResults(TaqManResult.get(params.taqManResult as Long))

        if (taqManSetInstance.save(flush:true))
        {
            redirect(controller: "taqMan", model: [taqManSet: taqManSetInstance])
        }
        else
        {
            flash.message = "Could not add TaqManResult to set."
            redirect(action: addToSet)
        }
    }

    def showDetails = {
        println params
        println "HALLO"
        println TaqManResult.get(params.id as Long)
        [taqManResultInstance: TaqManResult.get(params.id as Long)]
    }


    /**
     * triggered by addToSet.gsp if TaqManResult is supposed to be part of a new set, optionally along with a
     * selection of reference TaqManResults.
     */
    def createSet = {

        def taqManSetInstance = new TaqManSet(name: params.nameOfSet)

        if (taqManSetInstance) taqManSetInstance.addToTaqManResults(TaqManResult.get(params.taqManResult as Long))

        if (params.referenceSelect)
        {
            for(def taqManResult : params.list("referenceSelect"))
            {
                taqManSetInstance.addToTaqManResults(TaqManResult.get(taqManResult as Long))
            }
        }
        
        if (taqManSetInstance.save(flush:true))
        {
            redirect(controller: "taqMan", model: [taqManSet: taqManSetInstance])
        }
        else
        {
            flash.message = "Could not create set, maybe the name already exists?"
            redirect(action: addToSet)
        }
    }

    /*
    read detectors from CSV file and compare Levenshtein distance between them and the available TaqManAssays in the DB.
     */
    def checkDetectors = {
        List<String[]> rows = taqManResultService.getTaqManRows(params['attachment.id'])

        def detectorMap = [:]
        def uniqueSet = new HashSet()        
        rows.each{
            try{
                uniqueSet.add(it[DETECTOR])
            } catch(java.lang.ArrayIndexOutOfBoundsException e){

            }
        }
        
        uniqueSet.toList().each{ detector ->
            detectorMap[detector] = TaqManAssay.list().sort{StringUtils.getLevenshteinDistance(it.name, detector)}
        }

        return [taqManResultInstance:  TaqManResult.get(params.taqManResultId), detectors: uniqueSet.toList(), detectorMap: detectorMap]
    }


    /*
     *  Overwrite detectors with the selection from checkDetectors.gsp
     */
    def overwriteDetectors = {

        params["attachment.id"] = params.id
        params.remove("id")
        
        def taqManResultInstance = TaqManResult.get(params.taqManResultId)

        FileInputStream inputFile = taqManResultService.getFileStream(false, params['attachment.id'])

        def text = inputFile.text

        def detectors = params.detectors[1..-2].split(", ") as List
        
        def newDetectors = [] 
        
        detectors.each{ detector ->
            if(!it.equals(params[it].toString()))
                text = text.replace(detector.toString(), params[detector].toString())
                newDetectors.add(TaqManAssay.findByName(params[detector].toString()))
        }

        inputFile.close()

        FileOutputStream outputFile = taqManResultService.getFileStream(true, params['attachment.id'])
        outputFile.write(text.getBytes())
        outputFile.close()

        taqManResultInstance.detectorsAssigned = true
        taqManResultInstance.detectors = newDetectors

        if (!taqManResultInstance.samplesAssigned)
            redirect(action: "assignSamples", params:  [taqManResultId: taqManResultInstance.id, 'attachment.id': params['attachment.id']])
        else redirect(action: "createSet", params: [taqManResultId:  taqManResultInstance.id])
    }
}
