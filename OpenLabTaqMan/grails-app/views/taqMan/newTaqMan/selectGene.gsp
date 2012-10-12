<%@ page import="org.openlab.taqman.TaqManResult; org.openlab.taqman.TaqManAssay; org.openlab.taqman.TaqManSet; openlab.attachments.DataObjectAttachment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
        <g:set var="entityName"
               value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}"/>
        <title><g:message code="default.list.label" args="[entityName]"/></title>
    </head>
<body>
<div class="body">
    <h1>TaqMan Analysis</h1><br/>
    <g:if test="${message}">
        <div class="message">${message}</div>
    </g:if>

    <h3>Input settings (step 1 / 3)</h3><br>

    <div class="list" style="border: 1px solid grey; padding:15px;">
        
        <gui:accordion>
        <gui:accordionElement title="Filter options">

        <table>
        <thead><tr><th>Filter for a specific CellLine</th><th>Filter for a specific detector</th><th>Results from a specific user</th></tr></thead>
        <tr><td><p><g:checkBox id="wildTypeOnly" name="wildTypeOnly" checked="${false}"
                       onChange="document.getElementById('geneSelection').value='null';${remoteFunction(action: 'updateWildTypeOnly', update: 'selectCellLineData', params: '\'wildType=\'+this.checked')}"></g:checkBox> Consider only wildtype cell lines</p><br>

        <p><g:select id="geneSelection"
                     onChange="document.getElementById('wildTypeOnly').checked=false;${remoteFunction(action: 'updateSelectedGene', update: 'selectCellLineData', params: '\'selectedGene=\'+this.value')}"
                     name="selectedGene.id" from="${org.openlab.genetracker.Gene.list(sort: 'name')}" optionKey="id"
                     value="" size="9"/></p><br/>

        <div id="selectCellLineData"></div>
        </td><td>
        <g:select id="detectorSelection"
                    onChange="${remoteFunction(action: 'updateSelectedDetector', update: 'ajaxSelectTaqMan', params: '\'selectedDetector=\'+this.value')}"
                    name="selectedDetector.id" from="${org.openlab.taqman.TaqManAssay.list(sort: 'name')}" optionKey="id"
                    value="" size="10"/><br/>
        </td>
        <td>
            <g:select id="userSelection"
                    onChange="${remoteFunction(action: 'updateSelectedUser', update: 'ajaxSelectTaqMan', params: '\'selectedUser=\'+this.value')}"
                    name="selectedUser.id" from="${org.openlab.security.User.list()}" optionKey="id"
                    value="" size="10"/><br/>

        </td>
        </tr>
        </table>
        </gui:accordionElement>
        </gui:accordion>
        <g:form action="newTaqMan" method="post">

            <p>Skip lines at top of CSV files:
                <g:select name="skipLines" noSelection="['': 'Select number of lines to skip']" from="${1..100}"
                          value="29"/></p><br/> </br>
            <hr> <br/>

            <h2><b>Select TaqMan sets and / or results you want to include in the analysis:</b></h2><br/>

            <div class="message">Selected TaqMan results will be treated as one sample set.</div>

            <div id="ajaxSelectTaqMan">
                <g:render template="selectFiles"
                          model="${['taqManResults': TaqManResult.list(), 'taqManSets': TaqManSet.list()]}"/>
            </div>

            </p><br/>

            <g:submitToRemote update="body" id="_eventId_filesSelected" value="Continue with reference selection"></g:submitToRemote>
        </g:form><br><br>

    </div><br>

    <p>Reference (Algorithm implemented in R package 'ddCt'):</p><br>

    <p>Analysis of relative gene expression data using real-time quantitative PCR and the 2(-Delta -Delta C(T)) Method. KJ Livak and TD Schmittgen, Methods, Vol. 25, No. 4. (December 2001), pp. 402-408</p>
</body>
</html>
