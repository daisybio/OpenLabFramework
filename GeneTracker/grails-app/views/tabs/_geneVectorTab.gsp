	<g:if test="${vectors}">
	<div id="geneVectorTab">
	<div class="message">Keep in mind that changing a Vector Combination affects all associated CellLineData experiments.</div>

     <div id="geneVectorList">
         <table>
             <thead>
             <tr>
                 <th>Name</th>
                 <th>Gene</th>
                 <th>Vector</th>
                 <th>Notes</th>
                 <th>Last Update</th>
                 <th>&nbsp;</th>
                 <th>&nbsp;</th>
             </tr>
             </thead>
             <tbody>
             <g:each in="${geneVectorList}" status="i" var="geneVectorInstance">
                 <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                     <td>${geneVectorInstance}</td>
                     <td>${geneVectorInstance.genes}</td>
                     <td>${geneVectorInstance.vector}</td>
                     <td><g:editInPlace id="notes_${geneVectorInstance.id}"
                                        url="[controller: 'recombinant', action: 'editField', id: geneVectorInstance.id]"
                                        rows="1"
                                        cols="10"
                                        paramName="notes">
                        <g:if test="${geneVectorInstance.notes!=''}">
                            ${geneVectorInstance.notes}
                        </g:if>
                        <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
                     </g:editInPlace></td>
                     <td><g:formatDate type="date" date="${geneVectorInstance.lastUpdate}" /></td>
                     <td class="actionButtons">
                         <span class="actionButton">
                             <g:remoteLink controller="recombinant" action="show" params="[bodyOnly: true]" id="${geneVectorInstance.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
                         </span>
                     </td>
                     <g:if test="${geneVectorInstance.vector.type=='Integration (First)'}"><td class="actionButtons">
                         <span class="actionButton">
                             <g:remoteLink controller="cellLineData" action="create" params="${['firstRecombinant.id': geneVectorInstance.id, 'bodyOnly': true]}"  update="[success:'body',failure:'body']">Add cell line recombinant</g:remoteLink>
                         </span>
                     </g:if>
                     <g:if test="${geneVectorInstance.vector.type=='Integration (Second)'}"><td class="actionButtons">
                         <span class="actionButton">
                         <g:remoteLink controller="cellLineData" action="create" params="${['secondRecombinant.id': geneVectorInstance.id, 'bodyOnly': true]}"  update="[success:'body',failure:'body']">Add cell line recombinant</g:remoteLink>
                         </span>
                     </g:if>
                     </td>
                 </tr>
             </g:each>
             </tbody>
         </table>
     </div>

	 <div>
        Add a new recombinant:
	 	<g:formRemote name="addVectorCombinationForm" update="geneVectorTab" url="[controller: 'recombinant', action:'addRecombinantInTab']">
	 		 <g:hiddenField name="gene" value="${gene.id}"/>
             <g:select name="vector" from="${vectors}" optionKey="id" optionValue="label"/>
             <g:submitButton name="Add VectorCombination"/>
 		</g:formRemote>
	</div>
	 </div>
	 </g:if>
	 <g:else>Please create a vector first.</g:else>



