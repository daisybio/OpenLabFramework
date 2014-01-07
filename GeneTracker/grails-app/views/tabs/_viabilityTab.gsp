  <g:if test="${cellLineData}">
	<div id="viabilityTab">

        <div id="viabilityList">
            <table>
                <thead>
                <tr>
                    <th>Gene</th>
                    <th>CellLineData</th>
                    <th>Date</th>
                    <th>Percentage</th>
                    <th>Researcher</th>
                    <th>Notes</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${viabilities}" status="i" var="viability">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${viability.gene}</td>
                        <td>${viability.cellLineData}</td>
                        <td><g:formatDate type="date" date="${viability.date}" /></td>
                        <td>${viability.percentage}</td>
                        <td>${viability.researcher}</td>
                        <td><g:editInPlace id="notes_${viability.id}"
                                           url="[controller: 'viability', action: 'editField', id: viability.id]"
                                           rows="1"
                                           cols="10"
                                           paramName="notes">
                            <g:if test="${viability.notes!=''}">
                                ${viability.notes}
                            </g:if>
                            <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                        </g:editInPlace></td>
                        <td class="actionButtons">
                            <span class="actionButton">
                                <g:remoteLink controller="viability" action="delete" params="[bodyOnly: true]" id="${viability.id}" update="[success:'viabilityTab',failure:'viabilityTab']">Remove</g:remoteLink>
                            </span>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

	 <div>
        Add a new viability assay:<br/><br/>
        <g:form name="addViabilityForm">
        <g:hiddenField name="gene.id" value="${gene.id}"/>
        CellLine: <g:select from="${cellLineData}" optionKey="id" name="cellLineData.id"/>
        Date: <g:datePicker name="date" value="${new java.util.Date()}" precision="day" relativeYears="${-10..1}"/>
        Percentage: <g:field type="number" name="percentage" step="any" min="0" max="100"/>
	 	<g:submitToRemote controller="viability" update="viabilityTab" action="addViabilityInGeneTab" onFailure="alert('Operation not possible!')" value="Add viability"/>
         </g:form>
	</div>
	 </div>
  </g:if>
  <g:else>Please add a CellLineData experiment first.</g:else>

