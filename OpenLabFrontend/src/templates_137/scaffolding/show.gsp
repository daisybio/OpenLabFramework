<% import grails.persistence.Event %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    	<%	excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' << 'object' %>
    	<% historyProps = [] << 'creator' << 'creation_date' << 'last_modifier' << 'last_modified_date' %>
		<g:include action="additionalBoxes" id="\${${propertyName}?.id}"></g:include>	  
        <div style="width:60%; padding-left: 15px; padding-top:5px;">
            <!-- <h1><g:message code="default.show.label" args="[entityName]" /></h1>-->
            <h1><g:message code="default.show.label" args="[entityName]" /> \${${propertyName}}</h1>
            <g:if test="\${flash.message}">
            <div class="message">\${flash.message}</div>
            </g:if>
			<div class="dialog" style="width:100%;">
			<g:include action="mainView" id="\${${propertyName}?.id}"></g:include>
			</div>
            <div class="buttons">
                <g:form> 
                    <g:hiddenField name="id" value="\${${propertyName}?.id}" />
                    <g:hiddenField name="bodyOnly" value="true" />
                    <span class="button">
                    	<g:submitToRemote class="create" action="create" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.list.label', default: 'Create')}" />
                    	<g:submitToRemote class="list" action="list" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.list.label', default: 'List all')}" />
                    	<g:submitToRemote class="edit" action="edit" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.edit.label', default: 'Edit')}" />
                    	<g:submitToRemote update="[success:'body',failure:'error']" class="delete" action="delete" value="\${message(code: 'default.button.delete.label', default: 'Delete')}" before="if(!confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))return false;"/>
                   	</span>
                </g:form>
            </div>
     	</div>
     <div id="tabs" style="width:95%; padding-top: 5px; padding-left: 15px;">
     	<g:include action="tabs" id="\${${propertyName}?.id}"></g:include>
     </div>
    <script type="text/javascript">
    olfEvHandler.bodyContentChangedEvent.fire("\${${propertyName}?.toString()}", "\${${className}}" ,"\${${propertyName}?.id}");
    </script>
    </body>
</html>
