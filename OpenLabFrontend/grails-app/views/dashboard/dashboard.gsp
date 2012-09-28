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

			<div style="max-width:700px; width: 100%;border: 1px solid; margin-left:20px;">

            <div style="background: #f2f8ff; height:30px; text-align: left;"><h2>Recent changes by you</h2></div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Last Updated</th>
                 		    <th>Last Modifier</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${lastModifiedByUser}" status="i" var="userItem">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td class="actionButtons">
                                <span class="actionButton">
                                    <g:remoteLink controller="dataObject" action="showSubClass" params="[bodyOnly: true]" id="${userItem.id}" update="[success:'body',failure:'body']">${userItem}</g:remoteLink>
                                </span>
                            </td>
                            <td>${userItem.type.toString().capitalize()}</td>
                            <td><g:formatDate type="date" date="${userItem.lastUpdate}" /></td>
                            <td>${userItem.lastModifier}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
			</div>

			<div style="background: #f2f8ff; height:30px; text-align: left;"><h2>Recent changes by any users</h2></div>

			<div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Last Updated</th>
                            <th>Last Modifier</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${lastModifiedByAny}" status="i" var="anyItem">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td class="actionButtons">
                                <span class="actionButton">
                                    <g:remoteLink controller="dataObject" action="showSubClass" params="[bodyOnly: true]" id="${anyItem.id}" update="[success:'body',failure:'body']">${anyItem}</g:remoteLink>
                                </span>
                            </td>
                            <td>${anyItem.type.toString().capitalize()}</td>
                            <td><g:formatDate type="date" date="${anyItem.lastUpdate}" /></td>
							<td>${anyItem.lastModifier}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        </div>

        <g:render template="/layouts/basicStats" plugin="gene-tracker"/>
    </body>
</html>