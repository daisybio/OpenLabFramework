<% import grails.persistence.Event %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="\${flash.message}">
            <div class="message">\${flash.message}</div>
            </g:if>
            <g:hasErrors bean="\${${propertyName}}">
            <div class="errors">
                <g:renderErrors bean="\${${propertyName}}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
                <g:hiddenField name="id" value="\${${propertyName}?.id}" />
                <g:hiddenField name="version" value="\${${propertyName}?.version}" />
                <g:hiddenField name="bodyOnly" value="true" />
                <div class="dialog">
                    <table>
                        <tbody>
                   		<%	excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' << 'attachments' << 'attachedTo' << 'aclService' << 'aclUtilService' << 'objectIdentityRetrievalStrategy' << 'springSecurityService' << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate' 
                            props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
                            Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                            props.each { p ->
                                cp = domainClass.constrainedProperties[p.name]
                                display = (cp ? cp.display : true)        
                                if (display) { %>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="${p.name}"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></label>
                                </td>
                                <td valign="top" class="value \${hasErrors(bean: ${propertyName}, field: '${p.name}', 'errors')}">
                                    <%if (p.name == "sequence") {%>
                                    <g:textArea name="sequence" value="\${fieldValue(bean: ${propertyName}, field: '${p.name}').toUpperCase()}" cols="80" />
                                    <%}else if (p.name == "type") {%>
                                    \${fieldValue(bean: ${propertyName}, field: "${p.name}")}
                                    <%}else{ %>
                                    ${renderEditor(p)}
                                    <%} %>
                                </td>
                            </tr>
                        <%  }   } %>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
                    <g:hiddenField name="bodyOnly" value="true"></g:hiddenField>
                    <g:submitToRemote class="save" action="update" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.update.label', default: 'Update')}" />
                    <g:submitToRemote class="delete" action="delete" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.delete.label', default: 'Delete')}" />
                    </span>
                </div>
            </g:form>
        </div>
    <script type="text/javascript">
    olfEvHandler.bodyContentChangedEvent.fire("\${${propertyName}?.toString()}");
    </script>
    </body>
</html>
