
<%@ page import="openlab.attachments.DataObjectAttachment" %>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
		<g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataObjectAttachment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<!--<li><g:remoteLink params="${[bodyOnly: true]}" update="body" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:remoteLink></li>-->
			</ul>
		</div>
        <div id="list-dataObjectAttachment" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

            <div id="filter" class="boxShadow">
                <h2>Filter options:</h2>
                <div style="padding:15px;"/>
                <g:formRemote update="body" name="filterList" url="[controller: 'dataObjectAttachment', action:'list']">
                    <g:hiddenField name="bodyOnly" value="${true}"/>
                    Results per page: <g:select name="max" value="${params.max?:10}" from="${10..100}" class="range"/>
                    
                    
                    
                    <g:submitButton name="Filter"/>
                </g:formRemote>
                </div>
            </div>

			<table>
				<thead>
					<tr>
                        <g:remoteSortableColumn property="fileName" title="${message(code: 'dataObjectAttachment.fileName.label', default: 'Filename')}" />
                        <g:remoteSortableColumn property="fileUploadDate" title="${message(code: 'dataObjectAttachment.uploadDate.label', default: 'Uploaded')}" />
                        <g:remoteSortableColumn property="description" title="${message(code: 'dataObjectAttachment.description.label', default: 'Description')}" />
                        <th>Action</th>
					
                        <th/>
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataObjectAttachmentInstanceList}" status="i" var="dataObjectAttachmentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td>
                            <g:attachmentIcon type="${dataObjectAttachmentInstance.fileType}"/>
                            <g:link action="download" id="${dataObjectAttachmentInstance.id}" title="Show ${dataObjectAttachmentInstance.toString()}">${fieldValue(bean: dataObjectAttachmentInstance, field: "fileName")}</g:link></td>

                        <td valign="top" class="value">
                            <g:formatDate date="${dataObjectAttachmentInstance.fileUploadDate}" />
                        </td>

                        <td>
                            <g:editInPlace id="description_${dataObjectAttachmentInstance.id}"
                                           url="[action: 'editField', id: dataObjectAttachmentInstance.id]"
                                           rows="1"
                                           cols= "10"
                                           paramName="description">\${fieldValue(bean: dataObjectAttachmentInstance, field: "description")}
                            </g:editInPlace>
                        </td>

                        <td>

                            <g:form method="post" >
                                <g:hiddenField name="id" value="${dataObjectAttachmentInstance?.id}" />
                                <g:hiddenField name="version" value="${dataObjectAttachmentInstance?.version}" />
                                <g:hiddenField name="bodyOnly" value="${true}"/>
                                <g:link action="download" id="${dataObjectAttachmentInstance.id}"><g:attachmentIcon type="download"/></g:link> |
                                <g:submitToRemote class="edit" value="show" action="show" update="body"/>
                                <g:submitToRemote class="delete" value="delete" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"
                                                  action="delete" update="body"/>
                            </g:form>
                        </td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:remotePaginate total="${dataObjectAttachmentInstanceTotal?:0}" params="${params}" />
			</div>
		</div>
    <script type="text/javascript">
        olfEvHandler.bodyContentChangedEvent.fire("${dataObjectAttachmentInstance?.toString()}");
    </script>
	</body>
</html>
