	<div id="antibioticsTab">
	<gui:dataTable
	    id="dtAntibiotics"
	    draggableColumns="true"
	    columnDefs="[
	       [key:'id', hidden: 'true'],
	       [key:'antibiotics', formatter:'text', editor:[type:'dropDown', controller: 'antibiotics', 
	       		action:'tableDataChange', config:[dropdownOptions: unusedAntibiotics, disableBtns:true]],
	       		sortable:true, resizeable:true, label:'Antibiotics'],
	       [key:'concentration', formatter:'text', sortable:true, resizeable: true, label:'Concentration',
	       		editor:[controller: 'antibiotics', action:'tableDataChange', config:[disableBtns:true]]],
    		[key:'modifyUrls', sortable:false, resizable:false, label:'']	       		
		]"
		controller="antibiotics" action="antibioticsAsJSON"
		rowsPerPage="10"
		params="[id: params.id]"
		sortedBy="antibiotics"
		rowExpansion="false"
		collapseOnExpansionClick="false"
	 ></gui:dataTable>
	  	<span class="buttons">
		 <g:remoteLink controller="antibiotics" action="addTableRow" id="${params.id}" onSuccess="javascript:GRAILSUI.dtAntibiotics.requery();">Add Antibiotics</g:remoteLink>
	</span>
	</div>

