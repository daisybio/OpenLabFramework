<gui:tab label="CellLineData" cacheData="true">
	<div id="cellLineDataTab">
	<gui:dataTable
	    id="dtCellLineData"
	    draggableColumns="true"
	    columnDefs="[
	       [key:'id', sortable: true, resizable: true, label:'ID'],
	       [key:'cellLine', formatter:'text', sortable:true, resizeable: true, label:'CellLine'],
	       [key:'acceptor', formatter:'text', sortable:true, resizeable: true, label:'Acceptor'],
	       [key:'firstRecombinant', formatter:'text', sortable:true, resizeable: true, label:'First Vector Combination'],
	       [key:'secondRecombinant', formatter:'text', sortable:true, resizeable: true, label:'Second Vector Combination'],
	       [key:'cultureMedia', formatter:'text', sortable:true, resizeable: true, label:'Culture Media'],
	       [key:'modifyUrls', sortable:false, resizable:false, label:'']
		]"
		controller="cellLineData" action="cellLineDataAsJSON"
		rowsPerPage="10"
		params="[id: params.id]"
		sortedBy="id"
		scrollable="false"
		rowExpansion="false"
		collapseOnExpansionClick="false"
		rowClickNavigation="false"
	 ></gui:dataTable>
	 </div>
	 <div>
	 	<span class="buttons">
	 		<g:remoteLink controller="cellLineData" action="create" params="[bodyOnly:true]" update="[success:'body', failure:'body']">
	 			Add CellLineData
 			</g:remoteLink>
		</span>
	</div>
</gui:tab>
</div>
