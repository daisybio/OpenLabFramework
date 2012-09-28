	<div id="cellLineDataTab">
        <div id="cellLineDataList" class="list">
            <g:render plugin="gene-tracker" template="/layouts/cellLineDataList" model="['cellLineDataList':cellLineDataList]"></g:render>
        </div>
	 </div>
	 <div>
	 	<span class="buttons">
	 		<g:remoteLink controller="cellLineData" action="create" params="[bodyOnly:true]" update="[success:'body', failure:'body']">
	 			Add CellLineData
 			</g:remoteLink>
		</span>
	 </div>

