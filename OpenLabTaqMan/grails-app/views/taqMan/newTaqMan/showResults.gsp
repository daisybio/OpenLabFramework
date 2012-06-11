<%@ page import="org.openlab.taqman.TaqManSample; openlab.attachments.DataObjectAttachment" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="body" style="width:95%">
    <h1>TaqMan Analysis</h1><br/>
    <g:if test="${message}">
        <div class="message">${message}</div>
    </g:if>

    <h3>Results: (step 3 / 3)</h3><br>

    <g:form action="newTaqMan">
        <g:submitButton name="changeSettings" value="Change settings"/>
        <g:submitButton name="startNewTaqMan" value="Start a new TaqMan analysis"></g:submitButton>
    </g:form> <br/>

    <gui:tabView>
        <g:if test="${combinedFileName}">
        <gui:tab label="Combined evaluation" active="true">
            <table>
                <tr><td>Result graphic as PDF:</td><td><a
                        href="${createLink(action: 'downloadResult', controller: 'taqMan', params: [fileName: combinedFileName + '.pdf'])}">${combinedFileName + '.pdf'}</a>
                </td></tr>
                <tr><td>Result values (CSV2):</td><td><a
                        href="${createLink(action: 'downloadResult', controller: 'taqMan', params: [fileName: combinedFileName + '.csv'])}">${combinedFileName + '.csv'}</a>
                </td></tr>
            </table>

            <img align="middle"
                 src="${createLink(action: 'displayResultGraphic', controller: 'taqMan', params: [pngFileName: combinedFileName + '.png'])}"/>
        </gui:tab>
        <g:set var="combined" value="${true}"></g:set>
        </g:if>

        <g:each in="${sampleSets}" status="i" var="sampleSet">
            
            <gui:tab label="${sampleNames[sampleSet]}" active="${!combined && i == 0}">

                <div class="list" style="border: 1px solid grey; padding:15px;">
                    <table>
                        <tr><td>Result graphic as PDF:</td><td><a
                                href="${createLink(action: 'downloadResult', controller: 'taqMan', params: [fileName: fileNames[sampleSet] + '.pdf'])}">${fileNames[sampleSet] + '.pdf'}</a>
                        </td></tr>
                        <tr><td>Result values (tab delimited):</td><td><a
                                href="${createLink(action: 'downloadResult', controller: 'taqMan', params: [fileName: fileNames[sampleSet] + '.txt'])}">${fileNames[sampleSet] + '.txt'}</a>
                        </td></tr>
                        <tr><td>Result values (CSV2):</td><td><a
                                href="${createLink(action: 'downloadResult', controller: 'taqMan', params: [fileName: fileNames[sampleSet] + '.csv'])}">${fileNames[sampleSet] + '.csv'}</a>
                        </td></tr>
                    </table>
                    <g:if test="${warnings[sampleSet]?.entrySet()?.size() > 0}">
                    <gui:expandablePanel title="Warnings">
                        <g:each in="${warnings[sampleSet]?.entrySet()}"
                                var="entry">${entry?.key} : ${entry?.value} time(s) <br></g:each>
                    </gui:expandablePanel>
                    </g:if><br/>
                    <gui:expandablePanel title="Sample Legend">
                        <table>
                        <g:each in="${sampleLegend[sampleSet]}" var="sampleL">
                               <tr><td>${sampleL[0]}</td><td><g:if test="${sampleL[1]}">${sampleL[1]}</g:if>
                                   <g:if test="${sampleL[2]}">induced by ${sampleL[2]}</g:if></td></tr>
                        </g:each>
                        </table>
                    </gui:expandablePanel>
                </div>
                <img align="middle"
                     src="${createLink(action: 'displayResultGraphic', controller: 'taqMan', params: [pngFileName: fileNames[sampleSet] + '.png'])}"/>

            </gui:tab>
        </g:each>
    </gui:tabView>

</div>

</body>
</html>
