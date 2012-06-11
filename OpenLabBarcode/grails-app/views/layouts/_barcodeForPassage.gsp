<div id="barcodeMessage" style="padding-bottom:10px;"><h1>Print Barcode Label</h1>
</div>
<g:form method="post" >
	
	<g:if test="${passages.size()==0}">
	<font color="red">Please add a passage and hit refresh!</font>
	</g:if>
	<g:else>
	<p> Select a passage: <br>
	<g:select name="passageSelect" from="${passages}" optionKey="id"></g:select>
	</g:else>
    <div class="buttons">
        <span class="button"><g:remoteLink controller="barcode" action="scannerAddin" update="barcodeCreator">Switch to Scan</g:remoteLink></span>|
        	<span class="button">
        		<g:remoteLink controller="barcode" params="${params << [bodyOnly:true, id: session.nextBackId, className: session.nextBackController]}" update="barcodeCreator" action="renderBarcodeCreator">
	Refresh</g:remoteLink>
			</span>
        <span class="button"><g:submitToRemote action="createBarcodeWithPassage" update="barcodeCreator" name="create" class="save" value="Continue" /></span>


    </div>
</g:form>