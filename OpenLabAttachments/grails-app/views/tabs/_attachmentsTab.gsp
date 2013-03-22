	<div id="attachmentsTab">
        <g:if test="${dataObjectAttachmentInstanceList}">
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="fileName" title="${message(code: 'dataObjectAttachment.fileName.label', default: 'FileName')}" />
							<g:sortableColumn property="fileUploadDate" title="${message(code: 'dataObjectAttachment.uploadDate.label', default: 'Uploaded')}" />
							<g:sortableColumn property="description" title="${message(code: 'dataObjectAttachment.description.label', default: 'Description')}" />
							<th>action</th>                
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${dataObjectAttachmentInstanceList}" status="i" var="dataObjectAttachmentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>
                            <g:attachmentIcon type="${dataObjectAttachmentInstance.fileType}"/>
                            <g:link action="download" id="${dataObjectAttachmentInstance.id}">${fieldValue(bean: dataObjectAttachmentInstance, field: "fileName")}</g:link></td>

                            	<td valign="top" class="value">
                            		<g:formatDate date="${dataObjectAttachmentInstance.fileUploadDate}" />
                           		</td>

                            <td>
                                <g:editInPlace id="description_${dataObjectAttachmentInstance.id}"
									url="[controller: 'dataObjectAttachment', action: 'editField', id: dataObjectAttachmentInstance.id]"
									rows="1"
									cols= "10"
									paramName="description">
                                    <g:if test="${dataObjectAttachmentInstance.description == ''}">
                                        <img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/>
                                    </g:if>
                                    <g:else>\${fieldValue(bean: dataObjectAttachmentInstance, field: "description")}</g:else>
								</g:editInPlace>
							</td>
							
							<td>
								            
            					<g:form controller="dataObjectAttachment" method="post" >
                					<g:hiddenField name="id" value="${dataObjectAttachmentInstance?.id}" />
                					<g:hiddenField name="version" value="${dataObjectAttachmentInstance?.version}" />
                                    <g:hiddenField name="bodyOnly" value="${true}"/>
									<g:link controller="dataObjectAttachment" action="download" id="${dataObjectAttachmentInstance.id}"><g:attachmentIcon type="download"/></g:link> |
									<g:submitToRemote class="edit" update="body" action="show" controller="dataObjectAttachment" value="show"/>
									<g:remoteLink class="delete"
                                                      onSuccess="${remoteFunction(controller: 'dataObjectAttachment', action: 'renderAttachmentsTab', id: dataObject.id, update: 'attachmentsTab')}"
                                                      id = "${dataObjectAttachmentInstance?.id}"
                                                      controller="dataObjectAttachment"
                                                      action="deleteWithinTab"
                                                      value="delete"
                                                      before="if(!confirm('Are you sure?')) return false"
                                    >delete</g:remoteLink>
								</g:form>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </g:if>
        <g:else>
            There are no files attached to this object instance
        </g:else>
        </div>

