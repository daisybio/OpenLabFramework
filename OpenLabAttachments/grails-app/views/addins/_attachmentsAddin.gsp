<html>
<div id='attachments_${slot}' style="oveflow:hidden;" onmouseover="attachQ.select();">

<g:formRemote update="attachments_${slot}" name="uploadForm" id="uploadForm" url="[action:'createWithAddin', controller:'dataObjectAttachment']">

<div id="updateMe">
    <h2>Attachments</h2>
</div>

<g:hiddenField name="suggestQuery" value="true"/>
<g:hiddenField id="filesUploaded" name="filesUploaded" value=""/>

	<div id="files">
		<!--<input type="file" name="attachment"/>-->
        <uploader:uploader id="attachment" multiple="false" url="${[controller:'dataObjectAttachment', action:'uploadFile']}">

            <uploader:onComplete>
               document.getElementById('updateMe').innerHTML="<div class='message'>Upload successful</div>"

               var pathElt = document.createElement("input");
               pathElt.type = "hidden";
               pathElt.name = "filePath_" + id;
               pathElt.value = responseJSON.tempFile;

               var nameElt = document.createElement("input");
               nameElt.type = "hidden";
               nameElt.name = "fileName_" + id;
               nameElt.value = fileName;

               jQuery('#uploadForm').append(pathElt);
               jQuery('#uploadForm').append(nameElt);
               document.getElementById('filesUploaded').value = id;
               document.getElementById('selectDataObjectDiv').style.display = "block";
            </uploader:onComplete>
        </uploader:uploader>

    <!--<div style="background-color: #eeeeee;">Uploading TaqMan result? <g:checkBox name="taqMan" value="true"/></div>-->
    </div>
    <div id="selectDataObjectDiv" style="display: none; float:left;">
	 Select something to attach the file to:
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

    <div id="selection" style="height:100px; scroll:overflow;">
	<br/><br/>

    </div>
    <g:submitButton name="Submit"/>
    </div>
</g:formRemote>
</div>


<script>

YAHOO.util.Event.onDOMReady(function() {
	
	  GRAILSUI.attachQ.itemSelectEvent.subscribe(function(oSelf , elItem , oData) {
		var divSurround = document.createElement('div');
		var id = YAHOO.util.Dom.generateId(divSurround);

		var listEl = document.createElement('input');
		var listElId = YAHOO.util.Dom.generateId(listEl, 'attachmentLink_')
		listEl.setAttribute('name', 'attachmentLink_'+elItem[2][1])
		listEl.setAttribute('type', 'text');
        listEl.setAttribute('readonly');
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
</html>