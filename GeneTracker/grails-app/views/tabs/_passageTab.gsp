	<div id="passageTab">
        <table>
            <thead>
            <tr>
                <th>CellLineData</th>
                <th>Date</th>
                <th>Passage Nr.</th>
                <th>Researcher</th>
                <th>Notes</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${passages}" status="i" var="passage">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>${passage.cellLineData}</td>
                    <td><g:formatDate type="date" date="${passage.date}" /></td>
                    <td>${passage.passageNr}</td>
                    <td>${passage.researcher}</td>
                    <td><g:editInPlace id="notes_${passage.id}"
                                       url="[controller: 'passage', action: 'editField', id: passage.id]"
                                       rows="1"
                                       cols="10"
                                       paramName="notes">
                        <g:if test="${passage.notes!=''}">
                            ${passage.notes}
                        </g:if>
                        <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                    </g:editInPlace></td>
                    <td class="actionButtons">
                        <span class="actionButton">
                            <g:remoteLink controller="passage" action="delete" params="[bodyOnly: true]" id="${passage.id}" update="[success:'passageTab',failure:'passageTab']">Remove</g:remoteLink>
                        </span>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <div>
            Add a new passage:<br/><br/>
            <g:form name="addPassageForm">
                <g:hiddenField name="cellLineData.id" value="${cellLineData.id}"/>
                Date: <g:datePicker name="date" value="${new java.util.Date()}" precision="day" relativeYears="${-10..1}"/>
                Passage Nr: <g:textField name="passageNr" value="xx/xx/xx"/>
                <g:submitToRemote controller="passage" update="passageTab" action="addPassageInCellLineDataTab" onFailure="alert('Operation not possible!')" value="Add passage"/>
            </g:form>
        </div>
	 </div>
