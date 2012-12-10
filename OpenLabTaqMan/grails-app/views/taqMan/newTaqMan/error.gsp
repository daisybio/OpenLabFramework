<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:setProvider library="prototype"/>
        <meta name="layout" content="body" />
    </head>
    <body>
        <div class="body">
            <h1>TaqMan Analysis</h1><br/>
            <g:if test="${message}">
            <div class="message">${message}</div>
            </g:if>
            
            <h3>Something went wrong! Taqman analysis cannot be continued!</h3><br>

            <g:formRemote update="body" url="[controller: 'taqMan', action: 'newTaqMan', params: '']" name="secondStepForm">
            <g:hiddenField name="execution" value="${request.flowExecutionKey}"/>
			<g:submitButton name="startNewTaqMan" value="Start a new TaqMan analysis"></g:submitButton>
			</g:formRemote>
			</div>
    </body>
</html>
