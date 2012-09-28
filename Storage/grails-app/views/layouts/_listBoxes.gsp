	<div>
	<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:include controller="box" action="listBoxesInCompartment" id="${compartmentId}"/>
	 
	<div>
        <div style="padding:20px;">
            <g:form name="addBoxForm">
                 Description: <g:textField name="description"/>
                 <g:hiddenField name="compartment.id" value="${compartmentId}"/>
                 x: <g:field type="number" name="xdim" step="integer" min="1" max="100"/>
                 y: <g:field type="number" name="ydim" step="integer" min="1" max="100"/>
                <g:submitToRemote controller="box" action="addBox" update="boxListArea" value="Add new box" onFailure="alert('Operation not possible!')"/>
             </g:form>
        </div>

        <span class="buttons">
 	    <span class="button"><g:remoteLink controller="storage" before="if(!confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}')) return false;"
                                    update="body" action="removeCompartment" id="${compartmentId}" onFailure="alert('Operation failed!')">
 	        Delete Compartment
 		</g:remoteLink></span></span>
    </div>
	</div>