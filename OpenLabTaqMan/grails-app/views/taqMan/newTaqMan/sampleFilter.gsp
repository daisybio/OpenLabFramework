<%@ page import="openlab.attachments.DataObjectAttachment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':(bodyOnly?'body':'main')}" />
        <g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1>TaqMan Analysis</h1><br/>
            <g:if test="${message}">
            <div class="message">${message}</div>
            </g:if>
            
            <h3>Filter samples (step 2 / 4)</h3><br>
                
            <div class="list" style="border: 1px solid grey; padding:15px;">
            
            <g:form action="newTaqMan">
            
            <p> Please select samples to be included in the analysis </p><br>
            
            <g:select name="selectedSamples" from="${sampleList.sort()}" multiple="yes" /><br><br>
          
			<g:submitButton name="filterSamples" value="Continue"></g:submitButton>
			</g:form>
			</div>
    </body>
</html>
