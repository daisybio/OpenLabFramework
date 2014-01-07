<div id="barcodeMessage" style="padding-bottom:10px;"><h2>Print Barcode Label</h2>
</div>
<g:form method="post" >

	<g:if test="${passages.size()==0}">
	<font color="red">Please add a passage and hit refresh!</font>
	</g:if>
	<g:else>
	<div style="height:120px;"><p> Select a passage: <br>
	<g:select name="passageSelect" from="${passages}" optionKey="id"></g:select>
    </div>
	</g:else>
    <div class="buttons">
        <span class="button"><g:remoteLink controller="barcode" params="${params}" action="scannerAddin" update="barcodeCreator">Scan</g:remoteLink></span>
        <span class="button">
            <g:remoteLink controller="barcode" params="${params}" update="barcodeCreator" action="renderBarcodeCreator">
	Refresh</g:remoteLink>
			</span>
        <span class="button"><g:submitToRemote action="createBarcodeWithPassage" update="barcodeCreator" name="create" class="save" value="Continue" /></span>


    </div>
</g:form>