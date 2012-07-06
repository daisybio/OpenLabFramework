

<%@ page import="openlab.attachments.DataObjectAttachment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
        <g:set var="entityName" value="${message(code: 'dataObjectAttachment.label', default: 'DataObjectAttachment')}" />
    </head>
    <body>
        <div id="center">
        <div id="body" class="body">
           		<g:if test="${session.backController && !params.backLink}">
   			<div class="backbuttonDiv">
   				<span class="backbutton">
   					<g:remoteLink controller="${session.backController}" action="${session.backAction}" 
   					id="${session.backId}" update="[success: 'body']" params="'bodyOnly=true&backLink=true'">
   						<img src="${createLinkTo(dir:'images',file:'olf_back.png')}" alt="Go Back" />
   					</g:remoteLink>
   				</span>
			</div>
   		</g:if>
            <h1 style="padding-left:20px;">Edit file attachment</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${dataObjectAttachmentInstance}">
            <div class="errors">
                <g:renderErrors bean="${dataObjectAttachmentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${dataObjectAttachmentInstance?.id}" />
                <g:hiddenField name="version" value="${dataObjectAttachmentInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dataObjects"><g:message code="dataObjectAttachment.dataObjects.label" default="Data Objects" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataObjectAttachmentInstance, field: 'dataObjects', 'errors')}">
                                    <g:select name="dataObjects" from="${org.openlab.main.DataObject.list().sort( { a, b -> a.toString() <=> b.toString() } as Comparator)}" multiple="yes" optionKey="id" size="20" value="${dataObjectAttachmentInstance?.dataObjects*.id}" />
                                </td>
                            </tr>
                            
							 <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="dataObjectAttachment.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataObjectAttachmentInstance, field: 'description', 'errors')}">
                                    <g:textArea size="20" name="description" value="${dataObjectAttachmentInstance?.description}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="uploadDate"><g:message code="dataObjectAttachment.uploadDate.label" default="Uploaded" /></label>
                                </td>
                            	<td valign="top" class="value">
                            		<g:formatDate date="${dataObjectAttachmentInstance.fileUploadDate}" />
                           		</td>
                        	</tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
