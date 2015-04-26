<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="\${params.bodyOnly?'body_mobile':'mobile'}" />
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-${domainClass.propertyName}" class="content scaffold-show" role="main">
            <h3 style="padding-left:5px;">\${${propertyName}}</h3>
			<g:if test="\${flash.message}">
			<div class="message" role="status">\${flash.message}</div>
			</g:if>
			<div class="ui-grid-a">
                <% historyProps = [] << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate' %>
                <%  excludedProps = Event.allEvents.toList() << 'id' << 'version' << 'accessLevel'
				allowedNames = domainClass.persistentProperties*.name
				props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && !historyProps.contains(it.name)}
				Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
				props.each { p -> %>


                    <div id="${p.name}-label" class="ui-block-a"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></div>
					<%  if (p.isEnum()) { %>
						<div class="ui-block-b" aria-labelledby="${p.name}-label"><g:fieldValue bean="\${${propertyName}}" field="${p.name}"/></div>
                    <%  } else if (p.name == "sequence") { %>
                    <div class="ui-block-b" aria-labelledby="${p.name}-label">
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
                    </div>
                    <%  } else if (p.oneToMany) { %>
                    <div class="ui-block-b" aria-labelledby="${p.name}-label" style="width:80%;">
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
                        </ul></div>
                        <div class="ui-block-a" style="width: 100%;">
                            <g:form name="addOneToMany">
                                <g:hiddenField name="bodyOnly" value="\${true}"/>
                                <g:hiddenField name="propertyName" value="${p.name}"  />
                                <g:hiddenField name="referencedClassName" value="${p.referencedDomainClass?.fullName}"/>
                                <g:select from="\${${p.referencedDomainClass?.fullName}.list()}" name="selectAddTo" optionKey="id"/>
                                <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                                <g:submitToRemote onSuccess='jQuery("div[data-role=page]").page( "destroy" ).page();' action="addOneToMany" update="[success:'body',failure:'body']" value="Add" />
                            </g:form>
                        </div>

                    <%  } else if (p.manyToMany) { %>
                    <div class="ui-block-b" aria-labelledby="${p.name}-label" style="width:80%;">
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
                                        <g:submitToRemote onSuccess='jQuery("div[data-role=page]").page( "destroy" ).page();' action="removeManyToMany" update="[success:'body',failure:'body']" value="Remove" />
                                </li>
                                </g:form>
                            </g:each>
                            </g:if>
                            <g:else>
                                <i>None added</i>
                            </g:else>
                        </ul></div>
                        <div class="ui-block-a" style="width: 100%;">
                            <g:form name="addToProject">
                                <g:hiddenField name="bodyOnly" value="\${true}"/>
                                <g:hiddenField name="referencedClassName" value="${p.referencedDomainClass?.fullName}"/>
                                <g:select from="\${${p.referencedDomainClass?.fullName}.list()}" name="selectAddTo" optionKey="id"/>
                                <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                                <g:submitToRemote onSuccess='jQuery("div[data-role=page]").page( "destroy" ).page();' action="addManyToMany" update="[success:'body',failure:'body']" value="Add to" />
                            </g:form>
                        </div>
                    <%  } else if (p.manyToOne || p.oneToOne) { %>
                    <div class="ui-block-b" aria-labelledby="${p.name}-label">
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
                    </div>
					<%  } else if (p.type == Boolean || p.type == boolean) { %>
						<div class="ui-block-b" aria-labelledby="${p.name}-label"><g:checkBox name="${p.name}Checkbox" value="\${${propertyName}?.${p.name}}"
                                  onChange="\${g.remoteFunction(action:'editBoolean',id: ${propertyName}?.id, params: [property: '${p.name}'], onFailure:'alert("Could not save property change");')}"/></div>
					<%  } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
						<div class="ui-block-b" aria-labelledby="${p.name}-label"><g:formatDate date="\${${propertyName}?.${p.name}}" /></div>
                    <%  } else { %>
                    <div class="ui-block-b" aria-labelledby="${p.name}-label">
                        <g:set var="myInList" value="\${${propertyName}?.constraints.${p.name}.inList}"/>
                        <g:if test="\${myInList}">
                           <g:select from="\${myInList}" name="${p.name}Select" value="\${fieldValue(bean: ${propertyName}, field: '${p.name}')}"
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
                    </div>
					<%  } %>

			<%  } %>
			</div>
		</div>

        <div data-role="content" class="ui-content" role="main" id="tabSelection">
            <ul data-role="listview" data-divider-theme="c" data-inset="true" class="ui-listview ui-listview-inset ui-corner-all ui-shadow">
                <li data-role="list-divider" role="heading" class="ui-li ui-li-divider ui-bar-c ui-corner-top">
                    Look into...
                </li>
                <g:getInterestedMobileModules id='\${${propertyName}?.id}' domainClass='${domainClass.propertyName}'/>
            </ul>
        </div>

        <div id="tabs">

        </div>

	</body>
</html>
