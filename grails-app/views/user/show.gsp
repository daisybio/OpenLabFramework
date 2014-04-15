<head>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
	<title>Show User</title>
</head>

<body>
    <g:set var="entityName" value="User"/>

	<div class="body">
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
		<h1>Show User</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="dialog">
			<table>
			<tbody>

				<tr class="prop">
					<td valign="top" class="name">ID:</td>
					<td valign="top" class="value">${person.id}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Login Name:</td>
					<td valign="top" class="value">${person.username?.encodeAsHTML()}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Full Name:</td>
					<td valign="top" class="value">${person.userRealName?.encodeAsHTML()}</td>
				</tr>
				
				<tr class="prop">
					<td valign="top" class="name"><label for="email">E-Mail:</label></td>
					<td valign="top" class="value">${person.email?.encodeAsHTML()}
					</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Enabled:</td>
					<td valign="top" class="value">${person.enabled}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Roles:</td>
					<td valign="top" class="value">
						<ul>
						<g:each in="${roleNames}" var='name'>
							<li>${name}</li>
						</g:each>
						</ul>
					</td>
				</tr>

			</tbody>
			</table>
		</div>

		<div class="buttons">
			<g:form>
				<input type="hidden" name="bodyOnly" value="true" />
				<input type="hidden" name="id" value="${person.id}" />
				<span class="button"><g:submitToRemote action="edit" update="body" class="edit" value="Edit" /></span>
				<span class="button"><g:submitToRemote action="delete" update="body" class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
			</g:form>
		</div>

	</div>
</body>
