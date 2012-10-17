<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="\${params.bodyOnly?'body':'main'}" />
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
        <r:require module="export"/>
	</head>
	<body>
		<a href="#list-${domainClass.propertyName}" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:remoteLink params="\${[bodyOnly: true]}" update="body" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:remoteLink></li>
			</ul>
		</div>
        <div id="list-${domainClass.propertyName}" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="\${flash.message}">
			<div class="message" role="status">\${flash.message}</div>
			</g:if>

            <div id="filter" class="boxShadow">
                <h2>Filter options:</h2>
                <div style="padding:15px;"/>
                <g:formRemote update="body" name="filterList" url="[controller: '${domainClass.propertyName}', action:'list']">
                    <g:hiddenField name="bodyOnly" value="\${true}"/>
                    Results per page: <g:select name="max" value="\${params.max?:10}" from="\${10..100}" class="range"/>
                    <% if (domainClass.properties.find{ it.name == 'creator'}){%>
                    Creator: <g:select name="creatorFilter" from="\${org.openlab.security.User.list().collect{it.username}}"
                                       value="\${params.creatorFilter?:''}" noSelection="['':'']"/>
                    <% } %>
                    <% if (domainClass.properties.find{ it.name == 'lastModifier'}){%>
                    Last Modifier: <g:select name="lastModifierFilter" from="\${org.openlab.security.User.list().collect{it.username}}"
                                             value="\${params.lastModifierFilter?:''}" noSelection="['':'']"/>
                    <% } %>
                    <% if (domainClass.properties.find{ it.name == 'projects'}){%>
                    Project: <g:select name="projectFilter" from="\${org.openlab.main.Project.list().collect{it.name}}"
                                       value="\${params.projectFilter?:''}" noSelection="['':'']"/>
                    <% } %>
                    <g:submitButton name="Filter"/>
                </g:formRemote>
                </div>
            </div>

			<table>
				<thead>
					<tr>
					<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
						allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
						props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
						Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
						props.eachWithIndex { p, i ->
							if (i < 6) {
								if (p.isAssociation()) { %>
						<th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
					<%      } else { %>

						<g:remoteSortableColumn property="${p.name}" params="\${params}" title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" />
					<%  }   }   } %>
                        <th/>
					</tr>
				</thead>
				<tbody>
				<g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
					<tr class="\${(i % 2) == 0 ? 'even' : 'odd'}">
					<%  props.eachWithIndex { p, i ->
							if (i == 0) { %>
						<td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
					<%      } else if (i < 6) {
								if (p.type == Boolean || p.type == boolean) { %>
						<td><g:formatBoolean boolean="\${${propertyName}.${p.name}}" /></td>
					<%          } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
						<td><g:formatDate type="date" date="\${${propertyName}.${p.name}}" /></td>
					<%          } else if (p.isAssociation()){ %>
						<td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
                    <%          } else{ %>
                        <td><g:editInPlace id="${p.name}_\${${propertyName}.id}"
                                       url="[action: 'editField', id: ${propertyName}.id]"
                                       rows="1"
                                       cols= "10"
                                       paramName="${p.name}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}
                            </g:editInPlace>
                        </td>
                    <%  }   }   } %>
                        <td><g:remoteLink params="\${[bodyOnly: true]}" action="show" id="\${${propertyName}.id}" update="body">show</g:remoteLink></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:remotePaginate total="\${${propertyName}Total}?:0" params="\${params}" />
			</div>
            <export:formats params="\${params}"/>
		</div>
    <script type="text/javascript">
        olfEvHandler.bodyContentChangedEvent.fire("\${${propertyName}?.toString()}");
    </script>
	</body>
</html>
