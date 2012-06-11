    		<% import grails.persistence.Event %>
			<%=packageName%>
          	<%	excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' << 'attachments' << 'attachedTo' << 'aclService' << 'aclUtilService' << 'objectIdentityRetrievalStrategy' << 'springSecurityService' << 'object'%>
			<% historyProps = [] << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate' %>

                <table>
                    <tbody>
                    <%  props = domainClass.properties.findAll { !excludedProps.contains(it.name) && !historyProps.contains(it.name)}
                        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                        props.each { p -> %>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></td>
                            <%  if (p.isEnum()) { %>
                            <td valign="top" class="value">\${${propertyName}?.${p.name}?.encodeAsHTML()}</td>
                            <%  } else if (p.name == "sequence") { %>
                            <td valign="top" class="value">
                           		<g:editInPlace id="${p.name}"
									url="[action: 'editField', id:${propertyName}.id]"
									paramName="${p.name}"
									rows="10"
									cols="40"
									>
										<g:if test="\${fieldValue(bean: ${propertyName}, field: '${p.name}')}">
											<g:textArea name="sequence" value="\${fieldValue(bean: ${propertyName}, field: '${p.name}').toUpperCase()}" cols="80" />
										</g:if>
										<g:else><img src="\${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
								</g:editInPlace>
							</td>
                            <%  } else if (p.oneToMany) { %>
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                                    <li><g:remoteLink controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">\${${p.name[0]}?.encodeAsHTML()}</g:remoteLink></li>
                                </g:each>
                                </ul>
                                <!-- <modalbox:createLink params="['property':'${p.name}','className':'${p.referencedDomainClass?.fullName}', 'controllerName':'${p.referencedDomainClass?.shortName}']" id="\${${propertyName}.id}" action="editMany" title="Edit ${p.name}" width="500"><img src="\${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></modalbox:createLink> -->
                                <!-- <span class="simpleButton"><g:remoteLink controller="${p.referencedDomainClass?.propertyName}" action="create" update="[success: 'body', failure: 'body']"><img src="\${resource(dir:'images/skin',file:'olf_add.png')}" /></g:remoteLink></span> --> 
                            </td>
 	                       <%  } else if (p.manyToMany) { %>
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                                    <li><g:remoteLink controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">\${${p.name[0]}?.encodeAsHTML()}</g:remoteLink></li>
                                </g:each>
                                </ul>
                                <modalbox:createLink params="['property':'${p.name}','className':'${p.referencedDomainClass?.fullName}', 'controllerName':'${p.referencedDomainClass?.shortName}']" id="\${${propertyName}.id}" action="editMany" title="Edit ${p.name}" width="500"><img src="\${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></modalbox:createLink>
                                <!-- <span class="simpleButton"><g:remoteLink controller="${p.referencedDomainClass?.propertyName}" action="create" update="[success: 'body', failure: 'body']"><img src="\${resource(dir:'images/skin',file:'olf_add.png')}" /></g:remoteLink></span> --> 
                            </td>
                            <%  } else if (p.manyToOne || p.oneToOne) { %>
                            <td valign="top" class="value">
								<g:form>
								<g:hiddenField name="bodyOnly" value="true" />
								<g:editCollectionInPlace id="${p.name}"
									url="[action: 'editCollectionField', id:${propertyName}.id]"
									paramName="${p.name}"
									className="\${${className}}"
									referencedClassName="${p.referencedDomainClass?.fullName}">
										<g:hiddenField name="id" value="\${${propertyName}?.${p.name}?.id}"/>
										\${${propertyName}?.${p.name}?.encodeAsHTML()}
								</g:editCollectionInPlace>
								<g:if test="\${fieldValue(bean: ${propertyName}, field: '${p.name}')}">
									<g:submitToRemote controller="${p.referencedDomainClass?.propertyName}" action="show" update="[success:'body',failure:'error']" value="Show" />
								</g:if>
								</g:form>
                           	</td>
                            <%  } else if (p.manyToOne || p.oneToOne) { %>
                            <td valign="top" class="value"><g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${propertyName}?.${p.name}?.id}">\${${propertyName}?.${p.name}?.encodeAsHTML()}</g:link></td>
                            <%  } else if (p.type == Boolean.class || p.type == boolean.class) { %>
                            <td valign="top" class="value"><g:formatBoolean boolean="\${${propertyName}?.${p.name}}" /></td>
                            <%  } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
                            <td valign="top" class="value"><g:formatDate date="\${${propertyName}?.${p.name}}" /></td>
                            <%  } else if (p.name == "type"){ %>
                            <td valign="top" class="value">\${fieldValue(bean: ${propertyName}, field: '${p.name}')}</td>
                            <%  } else { %>
                            <td valign="top" class="value">
                           		<g:editInPlace id="${p.name}"
									url="[action: 'editField', id:${propertyName}.id]"
									rows="1"
									paramName="${p.name}">
										<g:if test="\${fieldValue(bean: ${propertyName}, field: '${p.name}')}">
											\${fieldValue(bean: ${propertyName}, field: '${p.name}')}
										</g:if>
										<g:else><img src="\${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
								</g:editInPlace>
							</td>
                            <%  } %>
                    <%  } %>
               	</tr></tbody></table>
