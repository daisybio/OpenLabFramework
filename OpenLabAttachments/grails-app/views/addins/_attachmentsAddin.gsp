<div id='attachments_${slot}' style="oveflow:hidden;" onmouseover="attachQ.select();">

<g:form enctype="multipart/form-data" name="uploadForm" action="createWithAddin" controller="dataObjectAttachment">

<table><tr><td><div id="updateMe"><h2 style="font-weight:bold;">Attachments</h2></div></td>
<td><g:submitButton name="submit" value="submit"/></td></tr></table>

<g:hiddenField name="suggestQuery" value="true"/>
<div>
	<div id="files">
		<input type="file" name="attachment"/>

    Uploading TaqMan result? <g:checkBox name="taqMan" value="true"/>
    </div>
	<div id="fileAddButton">
		<!-- <a href="#" onClick="createAnotherFileInput();return false;">Add another file</a> 
		<br/><br/>-->
	</div>
	<br/>
	<h3>Attach to? Search and select!</h3>
				<gui:autoComplete
		   	        minQueryLength="3"
			        queryDelay="0.5"
		        	id="attachQ"
		        	resultName="results"
		        	labelField="label"
		        	idField="id"
		        	controller="dataObjectAttachment"
		        	action="searchResultsAsJSON"
				/>
</div>
<div id="selection">
	<br/><br/>
</div>
</g:form>
</div>
</div>

<script>
function createAnotherFileInput(){

	var yafile = document.createElement('input');
	yafile.setAttribute('type', 'file');
	yafile.setAttribute('name', 'attachment');

	var files = document.getElementById('files');
	files.appendChild(yafile);

	var button = document.getElementById("fileAddButton")
	if(files.getElementsByTagName("input").length == 3)
		button.parentNode.removeChild(button);
	
}

YAHOO.util.Event.onDOMReady(function() {
	
	  GRAILSUI.attachQ.itemSelectEvent.subscribe(function(oSelf , elItem , oData) {
		var divSurround = document.createElement('div');
		var id = YAHOO.util.Dom.generateId(divSurround);

		var listEl = document.createElement('input');
		var listElId = YAHOO.util.Dom.generateId(listEl, 'attachmentLink_')
		listEl.setAttribute('name', 'attachmentLink_'+elItem[2][1])
		listEl.setAttribute('type', 'text');
		listEl.setAttribute('value', elItem[2][0]);
		divSurround.appendChild(listEl);

		var listRemove = document.createElement('a');
		listRemove.setAttribute('onClick', 'document.getElementById(\'selection\').removeChild(document.getElementById(\''+id+'\'));return false;');
		listRemove.setAttribute('href', '#');
		listRemove.appendChild(document.createTextNode('Remove'));
		divSurround.appendChild(listRemove);

		var divEl = document.getElementById("selection");
		divEl.appendChild(divSurround);
	  });

	  GRAILSUI.attachQ.textboxFocusEvent.subscribe(function(oSelf, elItem, oData) {
		  GRAILSUI.attachQ.getInputEl().value = '';
	  });
	});
</script>