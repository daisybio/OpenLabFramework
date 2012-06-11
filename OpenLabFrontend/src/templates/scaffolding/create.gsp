<% import grails.persistence.Event %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
		<div><g:include action="createAdditionalContent"></g:include></div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="\${flash.message}">
            <div class="message">\${flash.message}</div>
            </g:if>
            <g:hasErrors bean="\${${propertyName}}">
            <div class="errors">
                <g:renderErrors bean="\${${propertyName}}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
                <g:hiddenField name="bodyOnly" value="true"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        <%  excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' << 'attachments' << 'attachedTo' << 'aclService' << 'aclUtilService' << 'objectIdentityRetrievalStrategy' << 'springSecurityService' << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate'
                            props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
                            Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                            props.each { p ->
                            	if (!Collection.class.isAssignableFrom(p.type)) {
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
                                    <%}else{ %>
                                    ${renderEditor(p)}
                                    <%} %>
                                </td>
                            </tr>
                        <%  }   }   } %>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
                    	<g:submitToRemote class="save" action="save" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.list.label', default: 'Create')}" />
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>
