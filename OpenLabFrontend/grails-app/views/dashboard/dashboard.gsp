<html>
	<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    	<meta name="layout" content="${params.bodyOnly?'body':'main'}" />
        <g:setProvider library="prototype"/>
    
    	<title>Open Laboratory Framework</title>
	</head>
    <body>
        <div class="body">
            <h1>Welcome <sec:loggedInUserInfo field="username"/></h1><br/>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
			<div class="message"><h2>Recent changes by you:</h2></div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Last Updated</th>
                 		    <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${lastModifiedByUser}" status="i" var="userItem">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${userItem}</td>
                            <td>${userItem.type}</td>
                            <td><g:formatDate date="${userItem.lastUpdate}" /></td>
           					<td class="actionButtons">
								<span class="actionButton">
									<g:remoteLink controller="dataObject" action="showSubClass" params="[bodyOnly: true]" id="${userItem.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
								</span>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
			</div>
			<div class="message"><h2>Recent changes by any users:</h2></div>
			<div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Last Updated</th>
                            <th>Last Modifier</th>
                 		    <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${lastModifiedByAny}" status="i" var="anyItem">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${anyItem}</td>
                            <td>${anyItem.type}</td>
                            <td><g:formatDate date="${anyItem.lastUpdate}" /></td>
							<td>${anyItem.lastModifier}</td>
           					<td class="actionButtons">
								<span class="actionButton">
									<g:remoteLink controller="dataObject" action="showSubClass" params="[bodyOnly: true]" id="${anyItem.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
								</span>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>