<%@ page import="org.openlab.taqman.TaqManSet; openlab.attachments.DataObjectAttachment" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <h1>TaqMan Analysis</h1><br/>

    <g:if test="${message}">
        <div class="message">${message}</div>
    </g:if>

    <h3>Evaluation settings (step 2 / 3)</h3><br>

    <div class="list" style="border: 1px solid grey; padding:15px;">

        <table border="0">
            <g:form action="newTaqMan">

                <g:submitButton name="startNewTaqMan" value="Start a new TaqMan analysis"></g:submitButton><br><br>

                <tr><td>Select housekeeping genes:</td><td><g:select name="selectedHKgene" from="${detectorList.sort()}"
                                                                     value="${selectedHKgene}" multiple="yes"/></td>
                </tr>

                <g:each in="${sampleSets}" var="sampleSet">

                    <!--<tr><td>Select samples to include: </td><td> <g:submitButton name="filterSamples"
                                                                                     value="Select samples"></g:submitButton></td></tr>    -->
                    <tr><td>Select a reference sample for ${sampleNames[sampleSet]}:</td><td><g:select
                            name="${sampleSet}_selectedRefSample" from="${samples[sampleSet]}" value="${selectedRefSamples?selectedRefSamples[sampleSet]:samples[sampleSet][1]}"/></td></tr>

                </g:each>

                <tr><td>Select detector to plot per set (optional):</td> <td><g:select noSelection="['': '']"
                                                                                                 name="selectedDetector"
                                                                                                 from="${detectorList.sort()}"
                                                                                                 value="${selectedDetector ?: ''}"/></td>
                </tr>

                <tr><td>Logarithmic scale? (log2):</td> <td><g:select name="logarithmicScale"
                                                                        from="${['', 'log2', 'log10']}" value="${logarithmicScale ?: ''}"/></td></tr>

                <tr><td>Use robust statistics (median and MAD)?:</td><td><g:checkBox name="robustStatistics"
                                                                                     value="${robustStatistics ?: false}"/></td>
                </tr>

                <tr><td>Select a resolution for the result graphic:</td><td><g:select noSelection="['': '']"
                                                                                      name="graphicsResolution"
                                                                                      from="${['640x480', '1024x768', '1200x800', '1200x1024', '1440x900', '1600x1200', '1680x1050', '1920x1080', '1920x1200']}"
                                                                                      value="${graphicsResolution ?: '1200x800'}"></g:select></td>
                </tr>

                <tr><td>Group set results by:</td><td><g:select name="graphicsGroupBy" from="${['Detector', 'Sample']}"
                                                            value="${graphicsGroupBySample ?: 'Sample'}"/></td></tr>
                <g:if test="${samples.size() > 0}">
                <tr><td>Select CellLineData for set comparison</td>
                    <td><g:select name="selectedCellLineData" optionKey="value" optionValue="value" from="${samplesIntersection}" multiple="true" value="${selectedCellLineData}"></g:select></td></tr>
                </g:if>

                <tr><td>Select detector to plot in set comparison:</td> <td><g:select noSelection="['': '']"
                                                                                                 name="setComparisonDetector"
                                                                                                 from="${detectorList.sort()}"
                                                                                                 value="${setComparisonDetector ?: ''}"/></td>
                </tr>


                <tr><td colspan="2" align="right">
                    <g:submitButton name="referencesSelected" value="Process TaqMan data in R"></g:submitButton>
                </td></tr>
            </g:form>
        </table>
    </div>
</body>
</html>


