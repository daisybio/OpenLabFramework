       	<% import grails.persistence.Event %>
		<%=packageName%>
        <%	excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' %>
    	<% historyProps = [] << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate' %>
        <div style="padding-right:20px; padding-top: 20px; position:absolute; right:0;">
        	<% props = domainClass.properties.findAll { !excludedProps.contains(it.name) && historyProps.contains(it.name)}
        	if(props.size() > 0) { %>
        	<gui:expandablePanel title="History" expanded="true" closable="true">
                   <table style="border: 0;"><tbody>
                        <%props.each { p -> %>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></td>
                            <%  if (p.isEnum()) { %>
                            <td valign="top" class="value">\${${propertyName}?.${p.name}?.encodeAsHTML()}</td>
                            <%  } else if (p.oneToMany || p.manyToMany) { %>
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                                    <li>\${${p.name[0]}?.encodeAsHTML()}</li>
                                </g:each>
                                </ul>
                            </td>
                            <%  } else if (p.manyToOne || p.oneToOne) { %>
                            <td valign="top" class="value">\${${propertyName}?.${p.name}?.encodeAsHTML()}</td>
                            <%  } else if (p.type == Boolean.class || p.type == boolean.class) { %>
                            <td valign="top" class="value"><g:formatBoolean boolean="\${${propertyName}?.${p.name}}" /></td>
                            <%  } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
                            <td valign="top" class="value"><g:formatDate date="\${${propertyName}?.${p.name}}" /></td>
                            <%  } else { %>
                            <td valign="top" class="value">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
                            <%  } %>
                        </tr>
                    <%  } %>
                    </tbody>
                </table>
			</gui:expandablePanel>
			<%}%>

			<div style="padding-top:10px;">
				<gui:expandablePanel title="Operations" expanded="true" closable="false">
				 <ul>
					<g:includeOperationsForType domainClass="${domainClass.propertyName}" id="\${${propertyName}.id}"/>
				 </ul>
				</gui:expandablePanel>
			</div>
		</div>