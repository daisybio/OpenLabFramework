<div id="primerTab">
    <table>
        <thead>
        <tr>
            <th>Direction</th>
            <th>Label</th>
            <th>Sequence</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${primers}" status="i" var="primer">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>${primer.direction}</td>
                <td><g:editInPlace id="label_${primer.id}"
                                   url="[controller: 'primer', action: 'editField', id: primer.id]"
                                   rows="1"
                                   cols="10"
                                   paramName="label">
                    <g:if test="${primer.label!=''}">
                        ${primer.label}
                    </g:if>
                    <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                </g:editInPlace></td>
                <td><g:editInPlace id="sequence_${primer.id}"
                                   url="[controller: 'primer', action: 'editField', id: primer.id]"
                                   rows="1"
                                   cols="10"
                                   paramName="sequence">
                    <g:if test="${primer?.sequence!=''}">
                        ${primer?.sequence}
                    </g:if>
                    <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                </g:editInPlace></td>
                <td class="actionButtons">
                    <span class="actionButton">
                        <g:remoteLink controller="primer" action="delete" params="[bodyOnly: true]" id="${primer.id}" update="[success:'primerTab',failure:'primerTab']">Remove</g:remoteLink>
                    </span>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div>
        Add a new primer:<br/><br/>
        <g:form name="addPrimerForm">
            <g:hiddenField name="gene.id" value="${gene.id}"/>
            Direction: <g:select from="${["for", "rev"]}" name="direction"/>
            Label: <g:textField name="label"/>
            Sequence: <g:textField name="sequence"/>
            <g:submitToRemote controller="primer" update="primerTab" action="addPrimerInGeneTab" onFailure="alert('Operation not possible!')" value="Add primer"/>
        </g:form>
    </div>
</div>