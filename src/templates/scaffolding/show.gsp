<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="\${params.bodyOnly?'body':'main'}" />
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>

		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:remoteLink params="\${[bodyOnly: true]}" class="list" action="list" update="body"><g:message code="default.list.label" args="[entityName]" /></g:remoteLink></li>
				<li><g:remoteLink params="\${[bodyOnly: true]}" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:remoteLink></li>
                <li><g:remoteLink params="\${[bodyOnly: true]}" class="delete" action="delete" id="\${${propertyName}?.id}" update="body" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                    <g:message code='default.button.delete.label' default='Delete'/></g:remoteLink></li>
			</ul>
		</div>
        <g:render template="additionalBoxes" id="\${${propertyName}?.id}"/>
		<div id="show-${domainClass.propertyName}" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /> \${${propertyName}}</h1>
			<g:if test="\${flash.message}">
			<div class="message" role="status">\${flash.message}</div>
			</g:if>
			<ol class="property-list ${domainClass.propertyName}">
                <% historyProps = [] << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate' %>
                <%  excludedProps = Event.allEvents.toList() << 'id' << 'version' << 'accessLevel' << 'shared'
				allowedNames = domainClass.persistentProperties*.name
				props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && !historyProps.contains(it.name)}
				Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
				props.each { p -> %>

				<li class="fieldcontain">
                    <span id="${p.name}-label" class="property-label"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></span>
					<%  if (p.isEnum()) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:fieldValue bean="\${${propertyName}}" field="${p.name}"/></span>
                    <%  } else if (p.name == "sequence") { %>
                    <span class="property-value" aria-labelledby="${p.name}-label">
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
                    </span>
                    <%  } else if (p.oneToMany) { %>
                    <span class="property-value" aria-labelledby="${p.name}-label">
                        <ul>
                            <g:if test="\${${propertyName}.${p.name}}">
                            <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                                <g:form name="remove\${${p.name[0]}}Form">
                                    <li>
                                        <g:remoteLink controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">\${${p.name[0]}?.encodeAsHTML()}</g:remoteLink>

                                        <g:hiddenField name="bodyOnly" value="\${true}"/>
                                        <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                                        <g:hiddenField name="associatedId" value="\${${p.name[0]}?.id}"/>
                                        <g:hiddenField name="propertyName" value="${p.name}"/>
                                        <g:hiddenField name="referencedClassName" value="${p.referencedDomainClass?.fullName}"/>
                                        <g:hiddenField name="thisClassName" value="${className}"/>
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
                                <g:hiddenField name="bodyOnly" value="\${true}"/>
                                <g:hiddenField name="propertyName" value="${p.name}"  />
                                <g:hiddenField name="referencedClassName" value="${p.referencedDomainClass?.fullName}"/>
                                <g:select class="select2" from="\${${p.referencedDomainClass?.fullName}.list()}" name="selectAddTo" optionKey="id"/>
                                <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                                <g:submitToRemote action="addOneToMany" update="[success:'body',failure:'body']" value="Add" />
                            </g:form>
                    </span>
                    <%  } else if (p.manyToMany) { %>
                    <span class="property-value" aria-labelledby="${p.name}-label">
                        <ul>
                            <g:if test="\${${propertyName}.${p.name}.size() > 0}">
                            <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                                <g:form name="remove\${${p.name[0]}}Form">
                                <li>
                                    <g:remoteLink controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">\${${p.name[0]}?.encodeAsHTML()}</g:remoteLink>

                                        <g:hiddenField name="bodyOnly" value="\${true}"/>
                                        <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                                        <g:hiddenField name="associatedId" value="\${${p.name[0]}?.id}"/>
                                        <g:hiddenField name="propertyName" value="${p.referencedDomainClass?.propertyName}"/>
                                        <g:hiddenField name="referencedClassName" value="${p.referencedDomainClass?.fullName}"/>
                                        <g:hiddenField name="thisClassName" value="${className}"/>
                                        <g:submitToRemote action="removeManyToMany" update="[success:'body',failure:'body']" value="Remove" />
                                </li>
                                </g:form>
                            </g:each>
                            </g:if>
                            <g:else>
                                <i>None added</i>
                            </g:else>
                        </ul><br/><br/>
                        <g:form name="addToProject">
                            <g:hiddenField name="bodyOnly" value="\${true}"/>
                            <g:hiddenField name="referencedClassName" value="${p.referencedDomainClass?.fullName}"/>
                            <g:select class="select2" from="\${${p.referencedDomainClass?.fullName}.list()}" name="selectAddTo" optionKey="id"/>
                            <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                            <g:submitToRemote action="addManyToMany" update="[success:'body',failure:'body']" value="Add to" />
                        </g:form>
                    </span>
                    <%  } else if (p.manyToOne || p.oneToOne) { %>
                    <span class="property-value" aria-labelledby="${p.name}-label">
                        <div id="${p.name}Editable">
                            <div onclick="\${g.remoteFunction(action:'updateEditable',
                                                       id: ${propertyName}?.id,
                                                       params:[thisClassName: '${className}',
                                                       referencedClassName: '${p.referencedDomainClass?.fullName}',
                                                       propertyName: '${p.name}'],
                                                       update:'${p.name}Editable')}"
                        >
                            <g:if test="\${${propertyName}?.${p.name}}">\${${propertyName}?.${p.name}?.encodeAsHTML()}</g:if>
                            <g:else><img src="\${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>

                            </div>
                        </div>
                    </span>
					<%  } else if (p.type == Boolean || p.type == boolean) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:checkBox name="${p.name}Checkbox" value="\${${propertyName}?.${p.name}}"
                                  onChange="\${g.remoteFunction(action:'editBoolean',id: ${propertyName}?.id, params: [property: '${p.name}'], onFailure:'alert("Could not save property change");')}"/></span>
					<%  } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:formatDate date="\${${propertyName}?.${p.name}}" /></span>
                    <%  } else { %>
                    <span class="property-value" aria-labelledby="${p.name}-label">
                        <g:set var="myInList" value="\${${propertyName}?.constraints.${p.name}.inList}"/>
                        <g:if test="\${myInList}">
                           <g:select class="select2" from="\${myInList}" name="${p.name}Select" value="\${fieldValue(bean: ${propertyName}, field: '${p.name}')}"
                                     onChange="\${g.remoteFunction(action:'editInList',id: ${propertyName}?.id, params: '"${p.name}=" + this.value', onFailure:'alert("Could not save property change");')}"/>
                        </g:if>
                        <g:else>
                            <g:editInPlace id="${p.name}"
                                           url="[action: 'editField', id:${propertyName}.id]"
                                           rows="1"
                                           paramName="${p.name}">
                                <g:if test="\${fieldValue(bean: ${propertyName}, field: '${p.name}')}">
                                    \${fieldValue(bean: ${propertyName}, field: '${p.name}')}
                                </g:if>
                                <g:else><img src="\${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                            </g:editInPlace>
                        </g:else>
                    </span>
					<%  } %>
				</li>

			<%  } %>
			</ol>
		</div>

        <div id="tabs"/>
        <g:renderInterestedModules id='\${${propertyName}?.id}' domainClass='${domainClass.propertyName}'/>

        <script type="text/javascript">
            olfEvHandler.bodyContentChangedEvent.fire("\${${propertyName}?.toString()}", "\${${className}}" ,"\${${propertyName}?.id}");
        </script>
	</body>
</html>
