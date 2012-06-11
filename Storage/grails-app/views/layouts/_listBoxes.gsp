	<div style="width:80%;">
	<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
	<gui:dataTable
	    id="dtBoxes"
	    draggableColumns="true"
	    columnDefs="[
	       [key:'id', hidden: true],
	       [key:'description', formatter:'text', sortable:true, resizeable: true, label:'Description',
	       		editor:[controller: 'storage', action:'tableDataChange', config:[disableBtns:true]]],	
 		   [key:'xdim', formatter:'number',
				editor:[type:'dropDown', controller:'storage', action:'tableDataChange',
				config:[dropdownOptions: (1..50), disableBtns:true]],
				sortable:true, resizeable: true],
		   [key:'ydim', formatter:'number',
				editor:[type:'dropDown', controller:'storage', action:'tableDataChange',
				config:[dropdownOptions: (1..50), disableBtns:true]],
				sortable:true, resizeable: true],
		   [key:'lastUpdate', formatter:'date', sortable:true, resizeable: true],	
	       [key:'modifyUrls', sortable:false, resizable:false, label:'']
		]"
		controller="storage" action="listBoxesAsJSON"
		rowsPerPage="10"
		params="[id: compartmentId]"
		sortedBy="description"
		rowExpansion="false"
		collapseOnExpansionClick="false"
	 ></gui:dataTable>
	 
	<div><span class="buttons">
	 	<span class="button"><g:remoteLink controller="storage" action="addTableRow" id="${compartmentId}" onFailure="alert('Operation not possible!')" onSuccess="javascript:GRAILSUI.dtBoxes.requery();">
	 		Add new Box
 		</g:remoteLink></span>|
 	    <span class="button"><g:remoteLink controller="storage" before="if(!confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}')) return false;" update="body" action="removeCompartment" id="${compartmentId}" onFailure="alert('Operation failed!')">		
 	        Delete Compartment
 		</g:remoteLink></span>
	</span></div>
	</div>