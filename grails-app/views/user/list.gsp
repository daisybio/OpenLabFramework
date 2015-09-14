<head>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
	<title>User List</title>
</head>

<body>

	<div class="body">
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:remoteLink update="body" params="[bodyOnly:true]" class="create" action="create"><g:message code="default.new.label" args="['User']" /></g:remoteLink></li>
            </ul>
        </div>
		<h1>User List</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="list">
			<table>
			<thead>
				<tr>
					<g:remoteSortableColumn property="id" title="Id" />
					<g:remoteSortableColumn property="username" title="Login Name" />
					<g:remoteSortableColumn property="userRealName" title="Full Name" />
					<g:remoteSortableColumn property="email" title="E-Mail" />
					<g:remoteSortableColumn property="enabled" title="Enabled" />
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
			<g:each in="${userList}" status="i" var="user">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td>${user.id}</td>
					<td>${user.username?.encodeAsHTML()}</td>
					<td>${user.userRealName?.encodeAsHTML()}</td>
					<td>${user.email?.encodeAsHTML()}</td>
					<td>${user.enabled?.encodeAsHTML()}</td>
					<td class="actionButtons">
						<span class="actionButton">
							<g:remoteLink update="body" params="[bodyOnly:true]" action="show" id="${user.id}">Show</g:remoteLink>
						</span>
					</td>
				</tr>
			</g:each>
			</tbody>
			</table>
		</div>

		<div class="pagination">
			<g:remotePaginate total="${org.openlab.security.User.count()}" />
		</div>

	</div>
</body>
