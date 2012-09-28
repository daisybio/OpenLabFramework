<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <title>Check detectors in TaqMan file</title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<div style="float: right; margin-right: 20px;"><g:render template="status" model="${[taqManResultInstance: taqManResultInstance]}"/></div>
<div style="margin-left: 10px;">
    <h1>Please make sure the correct detector is assigned</h1>
    If a detector is not presented in the selection you might want to add it to the database:
    <g:form name="addDectector" action="addDetector">
        <g:hiddenField name="attachment.id" value="${params['attachment.id']}"/>
        <g:hiddenField name="taqManResultId" value="${params['taqManResultId']}"/>
        <g:textField name="name"/>
        <input type="submit" name="addDetectorButton" value="Add detector"/>
    </g:form><br/> <br/>

    <g:form id="${params['attachment.id']}" action="overwriteDetectors">

        <g:hiddenField name="detectors" value="${detectors}"/>
        <g:hiddenField name="taqManResultId" value="${taqManResultInstance.id}"/>
        <div style="width:300px;">
            <table>
                <thead><tr><th>Detector in input file</th><th>Detector in database</th></tr></thead>
                <g:each in="${detectors}" var="detector">
                    <tr>
                        <td>${detector}</td>
                        <td><g:select name="${detector}" from="${detectorMap[detector]}"/></td>
                    </tr>

                </g:each>
            </table>
        </div>
        <g:submitButton name="Submit"/>
    </g:form>
</div>
</body>
</html>