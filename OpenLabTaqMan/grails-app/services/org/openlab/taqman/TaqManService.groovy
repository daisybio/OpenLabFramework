package org.openlab.taqman

import org.rosuda.REngine.REngine
import openlab.attachments.DataObjectAttachment
import org.rosuda.REngine.Rserve.RConnection
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class TaqManService {

    def RperationsService

    static transactional = true

    /**
     * Method that opens a warningsFile and converts it to a map such that each error corresponds to a key and the
     * value reflects the number of occurences.
     * @param fileName
     * @return
     */
    def readFileToMap(def fileName) {

        def countingMap = [:]

        new File(fileName).eachLine{

            String line = it.toString()

            if(line.length() > 0)
            {
                if(countingMap.containsKey(line))
                {
                    int xtimes = countingMap[line]
                    countingMap[line] = ++xtimes
                }
                else
                {
                    countingMap[line] = 1
                }
            }
        }

        return countingMap;
    }

    /**
     * Method to create a subset of the datafiles in R that only contain the selected samples
     * @param flow
     * @return
     */
    def filterSampleList(def flow){

        if(flow.sampleList == flow.selectedSamples || flow.selectedSamples == null)
        {
            flow.filtered = false
        }

        else
        {
            flow.filtered = true

            //connect to Rserve
            def rConnection = RperationsService.getConnection()

            //set working directory to folder of last connection
            setWorkingDirectoryInR(rConnection, flow)

            def sampleList =  flow.selectedSamples.toList()
            String[] sampleArray = sampleList.toArray()

            //assign selected samples in R
            REngine rEngine = (REngine) rConnection
            rEngine.assign("selectedSamples", sampleArray)

            //process each file and filter samples
            filterSamples(rConnection, flow)

            //close connection
            rConnection.close()
        }
    }

    private filterSamples(def rConnection, def flow) {
        flow.selectedAttachmentIds.each {

            def fileName = DataObjectAttachment.get(Long.valueOf(it)).fileName
            def filteredFileName = "filtered_" + fileName

            rConnection.voidEval("CtDataRaw <- as.data.frame(read.table(file='${fileName}', header=TRUE, sep='\t'))")
            rConnection.voidEval("CtDataRawSubset <- CtDataRaw[CtDataRaw\$Sample %in% selectedSamples,]")
            rConnection.voidEval("save.image()")
            rConnection.voidEval("write.table(x=CtDataRawSubset, file='${filteredFileName}', quote=FALSE, sep='\t')")
        }
    }


    /**
     * Method that actually connects to R, submits options and applies ddCt. Transfers result files back to web server
     * @param flow
     * @return
     */
    def ddCt(def rFolder, def params, def referenceSample, def graphicsResolution, boolean filtered, def attachmentIds, def sampleLegend)
    {
        try{
            //connect to Rserve
            def rConnection = RperationsService.getConnection()
            
            //set working directory to folder of last connection
            setWorkingDirectoryInR(rConnection, rFolder)

            listInputFiles(attachmentIds, filtered, rConnection)

            //perform operations via Rserve (apply ddCt algorithm)
            ddCtStuffInR(rConnection, referenceSample, params, graphicsResolution, sampleLegend)

            String fileName = transferFiles(rConnection)

            rConnection.close();

            return(fileName)

        }catch(org.rosuda.REngine.Rserve.RserveException rse){
            log.error rse.getMessage()
            return("failure: Evaluation failure in R")
        }catch(java.io.IOException ioe){
            log.error ioe.getMessage()
            return("failure: One or more result files not found on Rserver")
        }
    }

    private void listInputFiles(def attachmentIds, boolean filtered, RConnection rConnection) {
    //list input files for processing in ddCt
        String[] fileList = attachmentIds.collect {
            if (!filtered) {
                DataObjectAttachment.get(Long.valueOf(it)).fileName
            }
            else {
                ("filtered_" + DataObjectAttachment.get(Long.valueOf(it)).fileName)
            }
        }.toArray()

        REngine rEngine = (REngine) rConnection
        rEngine.assign("fileVector", fileList)
    }

    private String transferFiles(RConnection rConnection) {
//create new file names
        String fileName = createFileName()

        //transfer result files to web server
        RperationsService.transferToClient(rConnection, fileName + ".png", "errBarchart.png")
        RperationsService.transferToClient(rConnection, fileName + ".pdf", "errBarchart.pdf")
        RperationsService.transferToClient(rConnection, fileName + ".txt", "allResults.txt")
        RperationsService.transferToClient(rConnection, fileName + ".csv", "allResults.csv")
        RperationsService.transferToClient(rConnection, fileName + "_warnings.txt", "warningFile.txt")
        return fileName
    }

    private String createFileName() {
        def timestamp = new Date()
        def tempDir = ConfigurationHolder?.config?.openlab?.temp?.dir ?: "temp/"
        def fileName = tempDir + timestamp.getTime().toString() + "_taqManResult"
        return fileName
    }

    /**
     * Method to set the working directory in R to the one stored in the flow scope.
     * @param rConnection
     * @param flow
     * @return
     */
    def setWorkingDirectoryInR(def rConnection, def rFolder) {
        rConnection.assign("folder", rFolder)
        rConnection.voidEval("setwd(folder)")
    }

    /**
     * Method where most of the R magic is happening
     * @param rConnection
     * @param params
     * @param width
     * @param height
     * @return
     */
    private ddCtStuffInR(def rConnection, def referenceSample, Map params, def graphicsResolution, def sampleLegend) {

        //assign selected housekeeping genes and samples in R

        String[] hkGenes = params.list("selectedHKgene").toArray()

        REngine rEngine = (REngine) rConnection
        rEngine.assign("name.reference.gene", hkGenes)

        rConnection.assign("name.reference.sample", referenceSample.toString())
        rConnection.assign("warningFile", "warningFile.txt")
        rConnection.voidEval("require(ddCt)")

        //create empty warningsFile
        rConnection.voidEval("file.create('warningFile.txt')")

        rConnection.voidEval("CtData <- CSVFrame(fileVector)")

        rConnection.voidEval("""result <- ddCtExpression(CtData, type='${params.robustStatistics?'median':'mean'}',
calibrationSample=name.reference.sample,	housekeepingGene=name.reference.gene, warningStream=warningFile)""")

        //create ggplot2 graphic (better control over scaling etc.)

        //extract expression levels and their error rates from result
        rConnection.voidEval("exprs <- assayData(result)\$exprs;err <- assayData(result)\$level.err")

        /**
         * Use ggplot2 for customized graphical output
         */
        preparePlot(rConnection, params)

        produceOutput(rConnection, graphicsResolution, sampleLegend)
    }

    private preparePlot(rConnection, Map params)
    {
        //reshape makes it accessible for ggplot2
        rConnection.voidEval("require(reshape2);require(ggplot2)")
        rConnection.voidEval("plotData <- cbind(melt(exprs), error=melt(err)[,3])")

        rConnection.voidEval("limits <- aes(ymax = value + error, ymin = value - error)")

        if (params.graphicsGroupBy == "Sample") {
            if (params.selectedDetector) {
                rConnection.voidEval("myPlotData <- subset(plotData, Detector=='${params.selectedDetector}')")
            }
            else {
                rConnection.voidEval("myPlotData <- plotData")
            }
            rConnection.voidEval("q <- qplot(Sample, value, data=na.omit(myPlotData), fill=factor(Sample), ylab='Expression fold change') + facet_wrap(~Detector)")
        }
        else if (params.graphicsGroupBy == "Detector") {
            rConnection.voidEval("q <- qplot(Detector, value, data=na.omit(plotData), fill=factor(Detector), ylab='Expression fold change') + facet_wrap(~Sample)")
        }

        rConnection.voidEval("q <- q + geom_bar(stat='identity') + geom_errorbar(limits, width=0.25) + opts(legend.position='none', axis.text.x=theme_text(angle=-45, hjust=0, vjust=1)) + geom_hline(aes(yintercept=1, alpha=0.3))")

        if (params.logarithmicScale == "log2") {
            rConnection.voidEval("q <- q + scale_y_log2()")
        }
        else if(params.logarithmicScale == "log10")
        {
            rConnection.voidEval("q <- q + scale_y_log10()")
        }
    }

    private produceOutput(rConnection, def graphicsResolution, def sampleLegend)
    {
        plotResults("errBarchart", graphicsResolution, rConnection)

        /**
         * Using the inbuild lattice plot functionality
         */
        //create png result graphic
        /*rConnection.voidEval("png('errBarchart.png', width=${width}, height=${height})")
          rConnection.voidEval("print(errBarchart(result, by='${params.graphicsGroupBy}'))")
          rConnection.voidEval("dev.off()")

          //create pdf result graphic
          rConnection.voidEval("pdf('errBarchart.pdf', paper='a4')")
          rConnection.voidEval("print(errBarchart(result, by='${params.graphicsGroupBy}'))")
          rConnection.voidEval("dev.off()")*/

        //write CSV output
        rConnection.voidEval("elistWrite(result,file='allResults.txt')")
        rConnection.voidEval("txtData <- read.delim('allResults.txt')")

        rConnection.voidEval("txtData\$cellLineData <- NA")
        rConnection.voidEval("txtData\$inducer <- NA")

        //append column with celllinedata information
        for(def sampleInfo in sampleLegend)
        {
            def sampleName = sampleInfo[0]
            def cellLineData = sampleInfo[1]
            def inducer = sampleInfo[2]

            if(cellLineData != "null")
            {
                rConnection.voidEval("txtData[txtData\$Sample=='${sampleName}',]\$cellLineData <- '${cellLineData}'")
            }
            if(inducer)
            {
                rConnection.voidEval("txtData[txtData\$Sample=='${sampleName}',]\$inducer <- '${inducer}'")
            }
            
        }
        rConnection.voidEval("write.csv2(txtData, 'allResults.csv')")
    }


    def produceCombinedPlot(def sets, def setNames, def rFolders, def selectedCellLineData, def setComparisonDetector, def graphicsResolution, def logarithmicScale)
    {
        def rConnection = RperationsService.getConnection();

        try{
        /* load each allResults.csv, combine them in one dataset and filter for rows with cellLineData != NA*/
        sets.eachWithIndex{ set, i->
            rConnection.voidEval("setwd('${rFolders[set]}')")
            rConnection.voidEval("txtData <- read.csv2('allResults.csv')")
            rConnection.voidEval("txtData\$taqManSet <- '${setNames[set]}'")
            
            //filter for detector and cellLineData
            rConnection.voidEval("txtData <- subset(txtData, Detector == '${setComparisonDetector}' & cellLineData %in% c(${selectedCellLineData.toString()[1..2]}))")
            if(i == 0) rConnection.voidEval("combined <- txtData[!is.na(txtData\$cellLineData),]")
            else rConnection.voidEval("combined <- rbind(combined, txtData[!is.na(txtData\$cellLineData),])")
        }
            
        println selectedCellLineData.toString()[1..-2]

        //create temporary directory
        def tempDir = createTempDir(rConnection)
        rConnection.voidEval("require(ggplot2)")

        //write results to CSV2
        rConnection.voidEval("write.csv2(combined, 'compareResults.csv')")
        log.info "writing TaqMan combined results to ${tempDir}"

        //plot results
        rConnection.voidEval("limits <- aes(ymax = exprs + level.err, ymin = exprs - level.err)")
        rConnection.voidEval("q <- qplot(cellLineData, exprs, data=subset(combined, !is.na(exprs)), fill=inducer, color=taqManSet, geom='bar', position='dodge', stat='identity')")
        
        if (logarithmicScale == "log2") {
            rConnection.voidEval("q <- q + scale_y_log2()")
        }
        else if(logarithmicScale == "log10")
        {
            rConnection.voidEval("q <- q + scale_y_log10()")
        }

        plotResults("compareResults", graphicsResolution, rConnection)
        
        def fileName = createFileName()

        RperationsService.transferToClient(rConnection, fileName + ".png", "compareResults.png")
        RperationsService.transferToClient(rConnection, fileName + ".csv", "compareResults.csv")
        RperationsService.transferToClient(rConnection, fileName + ".pdf", "compareResults.pdf")

        return fileName
            
        }catch(org.rosuda.REngine.Rserve.RserveException e){
            log.error rConnection.lastError
            return null
        }
    }

    private plotResults(def filename, def graphicsResolution, RConnection rConnection) {
        //get width and height
        def resolution = graphicsResolution ?: "1024x768"
        resolution = resolution.split("x")
        def width = resolution[0]
        def height = resolution[1]

        //print png result
        rConnection.voidEval("png('${filename}.png', width=${width}, height=${height})")
        rConnection.voidEval("print(q)")
        rConnection.voidEval("dev.off()")

        //print pdf result
        rConnection.voidEval("pdf('${filename}.pdf', paper='a4')")
        rConnection.voidEval("print(q)")
        rConnection.voidEval("dev.off()")
    }

    /**
     * Method that extracts the sample and detector names from the CSV files in R
     * @param flow
     * @return
     */
    def getReferencesFromCSVfiles(def attachmentIds, def skipLines, def rFolder)
    {
        def rConnection = RperationsService.getConnection();
        def sampleList = []
        def detectorList = []

        log.info "Rserve working directory: ${rFolder}"

        attachmentIds.each(){

            //get fileName for this attachment
            def dao = DataObjectAttachment.get(Long.valueOf(it))
            def pathToFile = dao.pathToFile
            def fileName = dao.fileName

            //transfer this file to server (ends up in /tmp/Rserv/conn???)
            RperationsService.transferToServer(rConnection, pathToFile, fileName);

            //read in files
            rConnection.voidEval("require(ddCt)")
            rConnection.assign("fileName", fileName)
            rConnection.voidEval("CtDataRaw <- read.csv(file=fileName, skip=${skipLines}, header=TRUE)")

            //create us a working directory that allows us to work across different Rconnections
            if(rFolder) setWorkingDirectoryInR(rConnection, rFolder)

            else
            {
                rFolder = createTempDir(rConnection)
            }

            //store data in our new wd
            rConnection.voidEval("write.table(x=CtDataRaw, file=fileName, quote=FALSE, sep='\t')")

            //read data again with ddCt method to obtain sample and gene names
            rConnection.voidEval("CtData <- CSVFrame(fileName)")

            //create unique sample and detector lists
            org.rosuda.REngine.RList samples = rConnection.eval("as.list(slot(CtData, 'coreData')\$Sample)").asList()
            org.rosuda.REngine.RList detectors = rConnection.eval("as.list(slot(CtData, 'coreData')\$Detector)").asList()

            def samplesFromThisFile = samples.collect{ it.asString() }
            def detectorsFromThisFile = detectors.collect{ it.asString() }

            sampleList.addAll(samplesFromThisFile)
            detectorList.addAll(detectorsFromThisFile)
        }

        //close connection (important, otherwise Rserve process for this connection will never terminate)
        rConnection.close()

        return([sampleList: sampleList, detectorList: detectorList, rFolder: rFolder])
    }

    private String createTempDir(def rConnection) {
        def timestamp = new Date().getTime().toString()
        rConnection.voidEval("setwd(tempdir())")
        rConnection.voidEval("dir.create(\"${timestamp}\")")
        rConnection.voidEval("setwd(\"${timestamp}\")")
        return rConnection.eval("getwd()").asString()
    }

}
