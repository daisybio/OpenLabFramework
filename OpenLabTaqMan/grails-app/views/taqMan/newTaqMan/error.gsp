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
                
          	<g:form action="newTaqMan">
            <g:hiddenField name="_eventId_startNewTaqMan" value="1"/>
			<g:submitToRemote value="Start a new TaqMan analysis"></g:submitToRemote>
			</g:form>
			</div>
    </body>
</html>
