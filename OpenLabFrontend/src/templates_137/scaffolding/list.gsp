<% import grails.persistence.Event %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" /> 
        <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="\${flash.message}">
            <div class="message">\${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        <%  excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' << 'attachments' << 'attachedTo' << 'aclService' << 'aclUtilService' << 'objectIdentityRetrievalStrategy' << 'springSecurityService' << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdate'
                            props = domainClass.properties.findAll { !excludedProps.contains(it.name) && it.type != Set.class }
                            Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                            props.eachWithIndex { p, i ->
                                if (i < 6) {
                                    if (p.isAssociation()) { %>
                            <th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
                   	    <%      } else { %>
                            <util:remoteSortableColumn action="list" update="body" property="${p.name}" title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" params="\${filterParams+[bodyOnly:true]}"/>
                        <%  }   }   } %>
                   		     <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                        <%  props.eachWithIndex { p, i ->
                                cp = domainClass.constrainedProperties[p.name]
                                  if(i < 6) {  
                                    if (p.type == Boolean.class || p.type == boolean.class) { %>
                            <td><g:formatBoolean boolean="\${${propertyName}.${p.name}}" /></td>
                        <%          } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
                            <td><g:formatDate date="\${${propertyName}.${p.name}}" /></td>
                        <%          } else if (!p.isAssociation() & (p.name != "type")) { %>
                            <!-- Replaced the original fieldValue td with one incorporating in-line editing -->
                            <td>
                         		<g:editInPlace id="${p.name}_\${${propertyName}.id}"
									url="[action: 'editField', id: ${propertyName}.id]"
									rows="1"
									cols= "10"
									paramName="${p.name}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}
								</g:editInPlace>
							</td>
						<% 			} else { %>
							<td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
                        <% } } }%>
           					<td class="actionButtons">
								<span class="actionButton">
									<g:remoteLink action="show" params="[bodyOnly: true]" id="\${${propertyName}.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
								</span>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <util:remotePaginate action="list" update="body" total="\${${propertyName}Total}" params="\${filterParams+[bodyOnly:true]}"/>
               	<span class="menuButton" style="padding-top:10px;">
                	<g:remoteLink update="body" params="[bodyOnly:true]" class="create" action="create">Add</g:remoteLink><filterpane:filterButton text="Filter Options" />
                	<filterpane:filterPane controller="${domainClass.propertyName}" domainBean="\${${className}}" />           	
           	        <g:form>
            			<g:hiddenField name="bodyOnly" value="true"/>
            			<span>Rows per page:</span><g:select from="\${(10..100).step(10)}" name="max" value="\${params.max?:10}"/>
            			<g:submitToRemote action="list" update="body" value="Change"/>
       				</g:form>
               	</span>
            </div>
            <export:formats params="\${filterParams}"/>
        </div>
    	<script type="text/javascript">
    	olfEvHandler.bodyContentChangedEvent.fire("\${${propertyName}?.toString()}");
    	</script>
    </body>
</html>
