<gui:tab label="Viability" cacheData="true">
  <g:if test="${cellLineDataNames}">
	<div id="viabilityTab">
	<gui:dataTable
	    id="dtViability"
	    draggableColumns="true"
	    columnDefs="[
	       [key:'id', hidden: 'true'],
		   [key:'date', formatter:'date', sortable:true, resizeable: true,
	       		editor:[controller:'viability', action:'tableDataChange', config:[disableBtns:true]]],
	       [key:'cellLineData', formatter:'text', sortable:true, resizeable: true, label:'Cell Line Data',
	       		editor:[type:'dropDown', controller: 'viability', action:'tableDataChange', config:[
	       		dropdownOptions: cellLineDataNames, disableBtns:true]]],
	       [key:'percentage', formatter:'text', editor:[controller: 'viability', action:'tableDataChange', config:[disableBtns:true]],
	       		sortable:true, resizable: true, label: '%'],
	       [key:'researcher', formatter:'text', editor:[type:'dropDown', controller: 'viability', 
	       		action:'tableDataChange', config:[dropdownOptions: userNames, disableBtns:true]],
	       		sortable:true, resizeable:true, label:'Researchers'],
	       [key:'notes', formatter:'text', sortable:true, resizeable: true, label:'Notes',
	       		editor:[controller: 'viability', action:'tableDataChange', config:[disableBtns:true]]],	
	       [key:'modifyUrls', sortable:false, resizable:false, label:'']
		]"
		controller="viability" action="viabilityAsJSON"
		rowsPerPage="10"
		params="[id: params.id]"
		sortedBy="date"
		rowExpansion="false"
		collapseOnExpansionClick="false"
	 ></gui:dataTable>
	 
	 <div><span class="buttons">
	 	<g:remoteLink controller="viability" action="addTableRow" id="${params.id}" onFailure="alert('Operation not possible!')" onSuccess="javascript:GRAILSUI.dtViability.requery();">
	 		Add Viability
 		</g:remoteLink>
	</span></div>
	 </div>
  </g:if>
  <g:else>Please add a CellLineData experiment first.</g:else>
</gui:tab>
</div>
