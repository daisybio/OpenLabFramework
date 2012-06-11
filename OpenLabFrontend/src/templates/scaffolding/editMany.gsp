<div id="modalBox" style="height: 300px; overflow:scroll;">
<g:form method="post" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
    <g:hiddenField name="backLink" value="true"/>
    <g:hiddenField name="id" value="\${params.id}" />
    <g:hiddenField name="version" value="\${${propertyName}?.version}" />
    <g:hiddenField name="propertyName" value="\${propertyName}" />
    <g:hiddenField name="className" value="\${className}" />
    <g:hiddenField name="bodyOnly" value="true"/>
	<table>
	<g:each status="i" in="\${all.sort()}" var="item">
		\${ ((i % 2) == 0) ? '<tr>' : ''}
		<td><input type="checkbox" id="\${item.id}" name="\${item.id}"
		<g:if test="\${selected.contains(item)}">checked</g:if></td>
		\${item}
		\${ ((i % 2) == 1) ? '</tr>' : ''}
	</g:each>
	</table>
    <div class="buttons">
       <span class="button"><g:submitToRemote onSuccess="Modalbox.hide();" update="[success:'body', failure:'body']" class="save" action="changeProperty" value="\${message(code: 'default.button.update.label', default: 'Update')}" />
       <g:submitToRemote class="create" controller="\${controllerName}" onSuccess="Modalbox.hide();" action="create" update="[success:'body',failure:'error']" value="\${message(code: 'default.button.list.label', default: 'Create')}" />
       </span>
    </div>
</g:form>
</div>