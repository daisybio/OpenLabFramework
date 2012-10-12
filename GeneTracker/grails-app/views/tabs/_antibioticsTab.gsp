	<div id="antibioticsTab">
        <div id="antibioticsList">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                <tr>
                    <th>Antibiotics</th>
                    <th>Concentration</th>
                    <th>Notes</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${antibioticsWithConc}" status="i" var="antibioticsInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${antibioticsInstance.antibiotics}</td>
                        <td>${antibioticsInstance.concentration}</td>
                        <td><g:editInPlace id="notes_${antibioticsInstance.id}"
                                           url="[controller: 'antibioticsWithConcentration', action: 'editField', id: antibioticsInstance.id]"
                                           rows="1"
                                           cols="10"
                                           paramName="notes">
                            <g:if test="${antibioticsInstance.notes!=''}">
                                ${antibioticsInstance.notes}
                            </g:if>
                            <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                        </g:editInPlace></td>
                        <td class="actionButtons">
                            <span class="actionButton">
                                <g:remoteLink controller="antibioticsWithConcentration" action="delete" id="${antibioticsInstance.id}" params="['cellLineData.id': cellLineData.id]" update="[success:'antibioticsTab',failure:'antibioticsTab']">Remove</g:remoteLink>
                            </span>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        <div>
            Add more antibiotics:
            <g:formRemote name="addAntibioticsForm" update="antibioticsTab" url="[controller: 'antibioticsWithConcentration', action:'addAntibioticsInTab']">
                <g:hiddenField name="cellLineData.id" value="${cellLineData.id}"/>
                <g:select name="antibiotics.id" from="${antibioticsList}" optionKey="id" optionValue="label"/>
                Concentration: <g:textField name="concentration"/>
                Notes: <g:textField name="notes"/>
                <g:submitButton name="Add antibiotics"/>
            </g:formRemote>
        </div>
	</div>

