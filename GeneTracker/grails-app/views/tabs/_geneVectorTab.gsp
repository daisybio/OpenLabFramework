<gui:tab label="Vector Combinations" cacheData="true">
	<g:if test="${vectors}">
	<div id="geneVectorTab">
	<div class="message">Keep in mind that changing a Vector Combination affects all associated CellLineData experiments.</div>
	<gui:dataTable
	    id="dtGeneVectors"
	    draggableColumns="true"
	    columnDefs="[
	       [key:'id', hidden: 'true'],
		   [key:'gene', formatter:'text', sortable:true, resizeable: true, label:'Gene'],
	       [key:'vector', formatter:'text', sortable:true, resizeable: true, label:'Vector',
	       		editor:[type:'dropDown', controller: 'recombinant', action:'tableDataChange', config:[
	       		dropdownOptions: vectors, disableBtns:true]]],
	       [key:'notes', formatter:'text', sortable:true, resizeable: true, label:'Notes',
	       		editor:[controller: 'recombinant', action:'tableDataChange', config:[disableBtns:true]]],
	       [key:'modifyUrls', sortable:false, resizable:false, label:'']
		]"
		controller="recombinant" action="recombinantsAsJSON"
		rowsPerPage="10"
		params="[id: params.id]"
		sortedBy="vector"
		rowExpansion="false"
		collapseOnExpansionClick="false"
	 ></gui:dataTable>
	 
	 <div><span class="buttons">
	 	<g:remoteLink controller="recombinant" action="addTableRow" id="${params.id}" onFailure="alert('Cannot add another vector! Define new vectors first.')" onSuccess="javascript:GRAILSUI.dtGeneVectors.requery();">
	 		Add VectorCombination
 		</g:remoteLink>
	</span></div>
	 </div>
	 </g:if>
	 <g:else>Please create a vector first.</g:else>
</gui:tab>
</div>


