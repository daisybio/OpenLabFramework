
<%@ page import="openlab.attachments.DataObjectAttachment" %>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
		<g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>

		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:remoteLink params="${[bodyOnly: true]}" class="list" action="list" update="body"><g:message code="default.list.label" args="[entityName]" /></g:remoteLink></li>
				<!--<li><g:remoteLink params="${[bodyOnly: true]}" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:remoteLink></li>-->
                <li><g:remoteLink params="${[bodyOnly: true]}" class="delete" action="delete" id="${dataObjectAttachmentInstance?.id}" update="body" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                    <g:message code='default.button.delete.label' default='Delete'/></g:remoteLink></li>
			</ul>
		</div>
		<div id="show-dataObjectAttachment" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /> ${dataObjectAttachmentInstance}</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dataObjectAttachment">
                
                

				<li class="fieldcontain">
                    <span id="dataObjects-label" class="property-label"><g:message code="dataObjectAttachment.dataObjects.label" default="Data Objects" /></span>
					
                    <span class="property-value" aria-labelledby="dataObjects-label">
                        <ul>
                            <g:if test="${dataObjectAttachmentInstance.dataObjects}">
                            <g:each in="${dataObjectAttachmentInstance.dataObjects}" var="d">
                                <g:form name="remove${d}Form">
                                    <li>
                                        <g:remoteLink controller="dataObject" action="show" id="${d.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">${d?.encodeAsHTML()}</g:remoteLink>

                                        <g:hiddenField name="bodyOnly" value="${true}"/>
                                        <g:hiddenField name="id" value="${dataObjectAttachmentInstance?.id}"/>
                                        <g:hiddenField name="associatedId" value="${d?.id}"/>
                                        <g:hiddenField name="propertyName" value="dataObjects"/>
                                        <g:hiddenField name="referencedClassName" value="org.openlab.main.DataObject"/>
                                        <g:hiddenField name="thisClassName" value="DataObjectAttachment"/>
                                        <g:submitToRemote action="removeOneToMany" update="[success:'body',failure:'body']" value="Remove" />
                                    </li>
                                </g:form>
                            </g:each>
                            </g:if>
                            <g:else>
                                <i>None added</i>
                            </g:else>
                        </ul><br/><br/>
                            <g:form name="addOneToMany">
                                <g:hiddenField name="bodyOnly" value="${true}"/>
                                <g:hiddenField name="propertyName" value="dataObjects"  />
                                <g:hiddenField name="referencedClassName" value="org.openlab.main.DataObject"/>
                                <g:select from="${org.openlab.main.DataObject.list()}" name="selectAddTo" optionKey="id"/>
                                <g:hiddenField name="id" value="${dataObjectAttachmentInstance?.id}"/>
                                <g:submitToRemote action="addOneToMany" update="[success:'body',failure:'body']" value="Add" />
                            </g:form>
                    </span>
                    
				</li>

			

				<li class="fieldcontain">
                    <span id="description-label" class="property-label"><g:message code="dataObjectAttachment.description.label" default="Description" /></span>
					
                    <span class="property-value" aria-labelledby="description-label">
                        <g:set var="myInList" value="${dataObjectAttachmentInstance?.constraints.description.inList}"/>
                        <g:if test="${myInList}">
                           <g:select from="${myInList}" name="descriptionSelect" value="${fieldValue(bean: dataObjectAttachmentInstance, field: 'description')}"
                                     onChange="${g.remoteFunction(action:'editInList',id: dataObjectAttachmentInstance?.id, params: '"description=" + this.value', onFailure:'alert("Could not save property change");')}"/>
                        </g:if>
                        <g:else>
                            <g:editInPlace id="description"
                                           url="[action: 'editField', id:dataObjectAttachmentInstance.id]"
                                           rows="1"
                                           paramName="description">
                                <g:if test="${fieldValue(bean: dataObjectAttachmentInstance, field: 'description')}">
                                    ${fieldValue(bean: dataObjectAttachmentInstance, field: 'description')}
                                </g:if>
                                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                            </g:editInPlace>
                        </g:else>
                    </span>
					
				</li>

			

				<li class="fieldcontain">
                    <span id="fileName-label" class="property-label"><g:message code="dataObjectAttachment.fileName.label" default="File Name" /></span>
					
                    <span class="property-value" aria-labelledby="fileName-label">
                                    ${fieldValue(bean: dataObjectAttachmentInstance, field: 'fileName')}     <g:link action="download" id="${dataObjectAttachmentInstance.id}"><g:attachmentIcon type="download"/></g:link>
                    </span>
					
				</li>

			

				<li class="fieldcontain">
                    <span id="fileType-label" class="property-label"><g:message code="dataObjectAttachment.fileType.label" default="File Type" /></span>
					
                    <span class="property-value" aria-labelledby="fileType-label">
                                    ${fieldValue(bean: dataObjectAttachmentInstance, field: 'fileType')}
                    </span>
					
				</li>

			

				<li class="fieldcontain">
                    <span id="fileUploadDate-label" class="property-label"><g:message code="dataObjectAttachment.fileUploadDate.label" default="File Upload Date" /></span>
					
						<span class="property-value" aria-labelledby="fileUploadDate-label"><g:formatDate date="${dataObjectAttachmentInstance?.fileUploadDate}" /></span>
                    
				</li>


			
			</ol>
		</div>

        <script type="text/javascript">
            olfEvHandler.bodyContentChangedEvent.fire("${dataObjectAttachmentInstance?.toString()}", "${DataObjectAttachment}" ,"${dataObjectAttachmentInstance?.id}");
        </script>
	</body>
</html>
