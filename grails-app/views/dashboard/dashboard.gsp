<html>
	<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    	<meta name="layout" content="body" />

    	<title>Open Laboratory Framework</title>
	</head>
    <body>
        <div class="body">
            <h1>Welcome <sec:loggedInUserInfo field="username"/></h1><br/>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>

			<div id="recentStuff">

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
    </body>
</html>