<%@ page import="openlab.attachments.DataObjectAttachment" %>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
		<g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-dataObjectAttachment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-dataObjectAttachment" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${dataObjectAttachmentInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${dataObjectAttachmentInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form name="editdataObjectAttachmentInstanceForm" method="post" >
				<g:hiddenField name="id" value="${dataObjectAttachmentInstance?.id}" />
				<g:hiddenField name="version" value="${dataObjectAttachmentInstance?.version}" />
                <g:hiddenField name="bodyOnly" value="${true}"/>
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitToRemote update="body" class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:submitToRemote update="body" class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
    <script type="text/javascript">
        olfEvHandler.bodyContentChangedEvent.fire("${dataObjectAttachmentInstance?.toString()}");
    </script>
	</body>
</html>
