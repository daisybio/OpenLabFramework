	<div id="passageTab">
	<gui:dataTable
	    id="dtPassages"
	    draggableColumns="true"
	    columnDefs="[
	       [key:'id', hidden: 'true'],
	       [key:'passageNr', formatter:'text', sortable:true, resizeable: true, label:'PassageNr',
	       		editor:[controller: 'passage', action:'tableDataChange', config:[disableBtns:true]]],
	       [key:'date', formatter:'date', sortable:true, resizeable: true,
	       editor:[controller:'passage', action:'tableDataChange', config:[disableBtns:true]]],
	       [key:'researcher', formatter:'text', editor:[type:'dropDown', controller: 'passage', 
	       		action:'tableDataChange', config:[dropdownOptions: userNames, disableBtns:true]],
	       		sortable:true, resizeable:true, label:'Researchers'],
	       [key:'notes', formatter:'text', sortable:true, resizeable: true, label:'Notes',
	       		editor:[controller: 'passage', action:'tableDataChange', config:[disableBtns:true]]],	
	       [key:'modifyUrls', sortable:false, resizable:false, label:'']
		]"
		controller="passage" action="passagesAsJSON"
		rowsPerPage="10"
		params="[id: params.id]"
		sortedBy="date"
		rowExpansion="false"
		collapseOnExpansionClick="false"
	 ></gui:dataTable>
 	<span class="buttons">
		 <g:remoteLink controller="passage" action="addTableRow" id="${params.id}" onSuccess="javascript:GRAILSUI.dtPassages.requery();">Add Passage</g:remoteLink>
	</span>
	 </div>
