<%@ page import="openlab.attachments.DataObjectAttachment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1>TaqMan Analysis</h1><br/>
            <g:if test="${message}">
            <div class="message">${message}</div>
            </g:if>
            
            <h3>Something went wrong! Taqman analysis cannot be continued!</h3><br>
                
          	<g:form action="newTaqMan">
			
			<g:submitButton name="startNewTaqMan" value="Start a new TaqMan analysis"></g:submitButton>
			</g:form>
			</div>
    </body>
</html>
