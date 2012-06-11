<head>
	<meta name="layout" content="main" />
	<title>User List</title>
</head>

<body>

	<div class="body">
		<h1>User List</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="list">
			<table>
			<thead>
				<tr>
					<g:sortableColumn property="id" title="Id" />
					<g:sortableColumn property="username" title="Login Name" />
					<g:sortableColumn property="userRealName" title="Full Name" />
					<g:sortableColumn property="email" title="E-Mail" />
					<g:sortableColumn property="enabled" title="Enabled" />
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

		<div class="paginateButtons">
			<g:paginate total="${org.openlab.security.User.count()}" />
			<span class="menuButton"><g:remoteLink update="body" params="[bodyOnly:true]" class="create" action="create">New User</g:remoteLink></span>
		</div>

	</div>
</body>
