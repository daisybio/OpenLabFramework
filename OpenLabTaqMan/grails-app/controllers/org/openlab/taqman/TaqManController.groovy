package org.openlab.taqman

import org.openlab.genetracker.CellLineData

/**
 * Controller responsible for keeping track of a TaqMan analysis web flow, including the actual analysis in R.
 * @author mlist
 *
 */

class TaqManController {

    def RperationsService
    def taqManService

    def index = {
        redirect(action: "newTaqMan")
    }

    def ajaxLoad = {
        redirect(action: "newTaqMan", params: [bodyOnly: true])
    }

    def testConnection = {
        [version: RperationsService.testConnection(RperationsService.getConnection())]
    }

    /**
     * this defines a grails web-flow (web-flow plugin) for a TaqMan analysis
     */
    def newTaqManFlow = {

        /**
         * VIEW STATE
         * This is the starting node. A form is presented that allows to select a gene / wildtype cell line and
         * subsequently a CellLineData object using Ajax requests. CSV file attachments are presented. The final submit
         * leads to the evaluation of the selected CSV files.
         */
        start {
            render(view: "selectGene")
            on("filesSelected") {
                println "filesSelected:" + params
                def sampleSets = []
                def newTaqManSet
                def newTaqManResults

                //needed for ajax webflowing
                flow.bodyOnly = true

                //summarize selected single results as temporary sample set
                if (params.taqManResultSelection)
                {
                    if(!params.newSet)
                    {
                        flash.message = "Please specify a name for the new set"
                        return error()
                    }
                
                    if(TaqManSet.findByName(params.newSet))
                    {
                        flash.message = "Set name already exists."
                        return error()
                    }

                    newTaqManResults = params.list("taqManResultSelection")
                }

                if (params.taqManSetSelection) {
                    for (def setId in params.list("taqManSetSelection")) {
                        sampleSets.add(setId)
                    }
                }

                //need to clear the session because not all objects in the session are serializable
                sessionFactory.getCurrentSession().clear()

                if(params.newSet)
                {
                    newTaqManSet = new TaqManSet(name: params.newSet)

                    newTaqManResults.each { newTaqManSet.addToTaqManResults(TaqManResult.get(it as Long))}
                
                   if(newTaqManSet.save(flush:true, failOnError: true))
                   {
                        sampleSets.add(newTaqManSet.id)
                   }
                }

                sessionFactory.getCurrentSession().clear()

                //reattach the attachments to the session
                flow.sampleSets = sampleSets
                params.bodyOnly = true

                if (sampleSets.size() == 0) {
                    flash.message = "You need to select at least one sample set or TaqManResult!"
                    return error()
                }

                return success()

            }.to "fetchSampleNames"
            on("return").to "start"
            on(Exception).to "handleException"
        }

        /**
         * ACTION STATE
         * This action is responsible for extracting the sample and detector names
         * using R, as well as to change the formatting to work with ddCt
         */
        fetchSampleNames {

            action {

                //upload files to R, convert as suitable input and get sample names

                try {
                    //initialize empty maps
                    def hkGenes = [:]
                    def samples = [:]
                    def rFolders = [:]
                    def sampleNames = [:]
                    def sampleSets = flow.sampleSets
                    def attachmentIds = [:]
                    def sampleLegend = [:]
                    def samplesIntersection

                    //iterate each taqman set
                    for (def sampleSetId in flow.sampleSets) {

                        def sampleSet = TaqManSet.get(sampleSetId as Long)

                        def selectedAttachmentsIds = sampleSet.taqManResults.collect{it.attachment.id}

                        def resultMap = taqManService.getReferencesFromCSVfiles(selectedAttachmentsIds, params.skipLines, rFolders[sampleSetId])
                        sampleNames[sampleSetId] = sampleSet.name

                        def myCellLineData = [:]

                        attachmentIds[sampleSetId] = selectedAttachmentsIds
                        sampleSet.taqManResults.each{
                           it.samples.each{s -> if(s.cellLineData) myCellLineData[s.cellLineData.id] = s.cellLineData.toString()}

                           if(sampleLegend[sampleSetId] != null) sampleLegend[sampleSetId].addAll(it.samples.collect{ s -> [s.sampleName, s.cellLineData.toString(), s.inducer?.toString()]})
                           else sampleLegend[sampleSetId] = it.samples.collect{ s -> [s.sampleName, s.cellLineData.toString(), s.inducer?.toString()]}
                        }

                        if(samplesIntersection) samplesIntersection = samplesIntersection.intersect(myCellLineData)
                        else samplesIntersection = myCellLineData

                        sampleLegend[sampleSetId].unique()

                        hkGenes[sampleSetId] = resultMap["detectorList"].unique()
                        samples[sampleSetId] = resultMap["sampleList"].unique()
                        rFolders[sampleSetId] = resultMap["rFolder"]
                    }

                    sessionFactory.getCurrentSession().clear()

                    flow.sampleLegend = sampleLegend
                    flow.sampleSets = sampleSets
                    flow.samples = samples
                    flow.sampleNames = sampleNames
                    flow.attachmentIds = attachmentIds
                    flow.samplesIntersection = samplesIntersection

                    def detectorList = hkGenes.values().toList().first()

                    for (def genes in hkGenes.values()) {
                        detectorList = detectorList.intersect(genes)
                    }

                    flow.detectorList = detectorList
                    flow.rFolders = rFolders

                } catch (org.rosuda.REngine.Rserve.RserveException rse) {
                    log.error rse.getMessage()
                    return error()
                }

                success()
            }

            on("success").to "selectReferences"
            on(Exception).to "handleException"
        }

        /**
         *  VIEW STATE
         *  Show execption
         */
        handleException {
            render(view: "error")
            on("startNewTaqMan").to "start"
        }

        /**
         * VIEW STATE
         * This is a view that allows for samples to be selected in a multi-select. It returns to the reference selection
         */
        sampleFilter {
            on("filterSamples") {
                flow.selectedSamples = params.selectedSamples
                if (!flow.selectedSamples.toList().contains(flow.selectedRefSample)) {
                    flow.selectedRefSample = flow.selectedSamples.toList().get(0)
                }
            }.to "selectReferences"
            on("goBackToReferenceSelection").to "selectReferences"
            on("startNewTaqMan").to "start"
        }

        /**
         * ACTION STATE
         * Here, the sample filter is applied
         */
        filterSamples {
            action {

                try {
                    taqManService.filterSampleList(flow)
                } catch (org.rosuda.REngine.Rserve.RserveException rse) {
                    log.error rse.getMessage()
                    flash.message = rse.getMessage()
                    return error()
                }

                success()
            }
            on("success").to "processTaqMan"
            on("error").to "sampleFilter"
            on(Exception).to "handleException"
        }

        /**
         * VIEW STATE
         * This action is called to render a view with housekeeping genes, reference samples and other options
         * to select. It can continue either to a sample filter or to the processing of the data using the given
         * options.
         */
        selectReferences {
            on("referencesSelected"){
                println "selectReferences" + params
                flow.selectedHKgene = params.list("selectedHKgene")
                
                if(params.list("selectedHKgene").size() == 0)
                {
                    flash.message = "At least one detector (housekeeping gene) must be selected for normalization!"
                    return error()
                }

                def selectedRefSamples = [:]

                for (sampleSet in flow.sampleSets) {
                    selectedRefSamples[sampleSet] = params[sampleSet.toString() + "_selectedRefSample"]
                }

                flow.selectedRefSamples = selectedRefSamples
                flow.graphicsGroupBy = params.graphicsGroupBy
                flow.graphicsResolution = params.graphicsResolution
                flow.robustStatistics = params.robustStatistics
                flow.logarithmicScale = params.logarithmicScale
                flow.selectedCellLineData = params.list("selectedCellLineData")
                flow.setComparisonDetector = params.setComparisonDetector

            }.to "filterSamples"
            on(Exception).to "handleException"
        }

        /**
         * ACTION STATE
         * Here, the pre-processed files are subjected to the ddCT algorithm. Plots are generated and files transferred back
         * to the web-server.
         */
        processTaqMan {
            action {

                def fileNames = [:]
                def warnings = [:]

                for (def sampleSetId in flow.sampleSets) {
                    String[] hkGenes = params.get("selectedHKgene").split(",")

                    //call the submethod that facilitates ddCt in R, returns the filename stem of all files.
                    def fileName = taqManService.ddCt(flow.rFolders[sampleSetId], params, hkGenes, params[sampleSetId.toString() + "_selectedRefSample"],
                            flow.graphicsResolution, flow.filtered, flow.attachmentIds[sampleSetId], flow.sampleLegend[sampleSetId])

                    //if something went wrong this is indicated by the return value
                    if (fileName.startsWith("failure")) {
                        flash.message = fileName
                        error()
                    }

                    else {

                        fileNames[sampleSetId] = fileName

                        //read in warnings from the corresponding file
                        if ((new File(fileName + "_warnings.txt")).exists()) {
                            warnings[sampleSetId] = taqManService.readFileToMap(fileName + "_warnings.txt")
                        }
                        else {
                            warnings[sampleSetId] = ["no warnings": 0]
                        }
                    }
                }

                def combinedFileName = taqManService.produceCombinedPlot(flow.sampleSets, flow.sampleNames, flow.rFolders, flow.selectedCellLineData, flow.setComparisonDetector, flow.graphicsResolution, flow.logarithmicScale)

                //sessionFactory.getCurrentSession().clear()

                //store the fileName for the view to create the links
                flow.combinedFileName = combinedFileName
                flow.fileNames = fileNames
                flow.warnings = warnings

                success()
            }

            on("success").to "showResults"
            on(Exception).to "handleException"
            on("error").to "selectReferences"
        }

        /**
         * VIEW STATE
         * shows the result plot with download options and warnings, allows to go back to the
         * reference state to change options and try again.
         */
        showResults {
            on("changeSettings").to "selectReferences"
            on("startNewTaqMan").to "start"
        }
    }

    /**
     * Action that renders a CellLineData select box upon selection of a gene
     */
    def updateSelectedGene = {

        if (params.selectedGene != "null") {
            def cellLineDataList = org.openlab.genetracker.CellLineData.withCriteria {
                or {
                    firstRecombinant
                            {
                                genes
                                        {
                                            eq("id", Long.valueOf(params.selectedGene))
                                        }
                            }
                    secondRecombinant
                            {
                                genes
                                        {
                                            eq("id", Long.valueOf(params.selectedGene))
                                        }
                            }

                }
            }

            render(template: "selectCellLineData", model: ["cellLineData": cellLineDataList], plugin: "open-lab-taq-man")
        }

        else render "Please select a gene!"
    }

    /**
     * Action that renders a select box upon ticking of the respective checkbox for wildtype cell lines
     */
    def updateWildTypeOnly = {

        if (params.wildType == "true") {
            def cellLineDataList = org.openlab.genetracker.CellLineData.withCriteria {
                isNull("firstRecombinant")
                isNull("secondRecombinant")
            }

            render(template: "selectCellLineData", model: ["cellLineData": cellLineDataList], plugin: "open-lab-taq-man")
        }
        else render "Please select a gene!"
    }

    /**
     * Action that renders a list of CSV files in a final form for submission to the next state of the webflow.
     */
    def updateSelectedCellLineData = {

        def myTaqManSets = new HashSet()
        def myTaqManResults = new HashSet()

        if (params.selectedCellLineData != "null") {
            
            /* find directly attached taqManResults */
            def attachments = openlab.attachments.DataObjectAttachment.withCriteria {
                eq("fileType", "CSV")
                createAlias("dataObjects", "dos")
                eq("dos.id", Long.valueOf(params.selectedCellLineData))
            }

            if (attachments) {
                for (def attachment in attachments) {
                    def resultsForThisAttachment = TaqManResult.findByAttachment(attachment)
                    if (resultsForThisAttachment) myTaqManResults.addAll(resultsForThisAttachment)
                }
            }
            
            /* find taqManResults only linked through a sample */
            def cellLineData = CellLineData.get(params.selectedCellLineData as Long)

            TaqManSample.findAllByCellLineData(cellLineData).each{
                myTaqManResults.add(it.taqManResult)
            }
            

            if (myTaqManResults) {
                def newTaqManSets = TaqManSet.withCriteria {

                    taqManResults
                    {
                        'in'("id", myTaqManResults.collect {res -> res.id as long})
                    }
                }

                if (newTaqManSets) myTaqManSets.addAll(newTaqManSets)
            }

            if (!myTaqManSets && !myTaqManResults)
                render NO_RESULTS

            else
            {
                if (flash.myTaqManSets) myTaqManSets = myTaqManSets.intersect(flash.myTaqManSets)
                if (flash.myTaqManResults) myTaqManResults = myTaqManResults.intersect(flash.myTaqManResults)

                flash.myTaqManSets = myTaqManSets
                flash.myTaqManResults = myTaqManResults

                render(template: "selectFiles", model: ["taqManSets": myTaqManSets, "taqManResults": myTaqManResults], plugin: "open-lab-taq-man")
            }
        }
        else render "Please select a CellLine dataset!"
    }

    private static final String NO_RESULTS = "No TaqMan results or sets have been added to the database for this selection."
    
    def updateSelectedUser = {

        def user = org.openlab.security.User.get(params.selectedUser as Long)
        
        def myTaqManSets = TaqManSet.findAllByCreator(user)
        def myTaqManResults = TaqManResult.findAllByCreator(user)
        
        if (flash.myTaqManSets) myTaqManSets = myTaqManSets.intersect(flash.myTaqManSets)
        if (flash.myTaqManResults) myTaqManResults = myTaqManResults.intersect(flash.myTaqManResults)
        
        if (!myTaqManSets && !myTaqManResults)
            render NO_RESULTS

        else{
         flash.myTaqManSets = myTaqManSets
         flash.myTaqManResults = myTaqManResults
         render(template:  "selectFiles", model: ["taqManSets": myTaqManSets, "taqManResults": myTaqManResults], plugin:  "open-lab-taq-man")
        }
    } 
    
    def updateSelectedDetector = {

        def detector = TaqManAssay.get(params.selectedDetector as Long)
        
        def myTaqManResults = TaqManResult.withCriteria{
            detectors
                    {
                        eq("id", detector.id)
                    }
        }
        def myTaqManSets
        
        if (myTaqManResults) 
        {
            myTaqManSets = TaqManSet.withCriteria {

                taqManResults
                        {
                            'in'("id", myTaqManResults.collect {res -> res.id as long})
                        }
            }
        }

        if (flash.myTaqManSets) myTaqManSets = myTaqManSets.intersect(flash.myTaqManSets)
        if (flash.myTaqManResults) myTaqManResults = myTaqManResults.intersect(flash.myTaqManResults)
        
        if (!myTaqManSets && !myTaqManResults)
            render NO_RESULTS

        else{
            flash.myTaqManSets = myTaqManSets
            flash.myTaqManResults = myTaqManResults
            render(template:  "selectFiles", model: ["taqManSets": myTaqManSets, "taqManResults": myTaqManResults], plugin:  "open-lab-taq-man")
        }
    }

    /**
     * Method to open the result plot and deliver it as binary stream for display
     */
    def displayResultGraphic = {

        def file = new File(params.pngFileName)
        if (file.exists()) {
            response.setContentType("image/png")
            response.outputStream << file.newInputStream()
        }
    }

    /**
     * Method to open the results and deliver them as binary stream for download
     */
    def downloadResult = {

        def file = new File(params.fileName)
        if (file.exists()) {
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")

            response.outputStream << file.newInputStream()
        }
    }
}
