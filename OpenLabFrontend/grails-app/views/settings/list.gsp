<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <g:setProvider library="prototype"/>
    <r:require module="scriptaculous"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />

    <title>Settings</title>
</head>

<body>
<div class="body" style="width:97%;">
	<h1>Settings</h1>
     <g:if test="${flash.message}">
       <div class="message">${flash.message}</div>
     </g:if>
   <div style="float:right; width:40%;">
    <div style="padding-bottom:20px;">
    <table height=70><tr><th width=50%>Search:</th><th>Result</th></tr>	
   	<tr><td><gui:autoComplete
   	        minQueryLength="3"
	        queryDelay="0.5"
        	id="settingsAutoComplete"
        	resultName="settings"
        	labelField="KEY"
        	idField="KEY"
        	controller="settings"
        	action="settingsAsJSON"
	/></td><td><div id="showResult">&nbsp;<br></br></div></td></tr></table>
	</div>
	<div>
	<g:form name="addSetting" action="add">
	<table>
	  <tr>
	  	<th colspan=2>Add new Setting</th>
	  </tr>
	  <tr>
		<td><input onfocus="if(this.value=='key'){this.value='';}" type="text" name="key" value="key"/></td>
		<td><input onfocus="if(this.value=='value'){this.value='';}" type="text" name="value" value="value"/></td>
	  </tr>
	</table>
	<div class="buttons">
   		<span class="button"><g:actionSubmit class="create" action="add" value="Add"/></span>
	 </div>
	 </g:form>
	</div>
  </div>

    <div class="list" style="width:50%;">
	<table>
		<thead>
		<tr><th>Key</th><th>Value</th><th>&nbsp;</th></tr>
		</thead>
		<tbody>
		<g:each in="${settingsList}" var="setting">
			<tr>
           		<td>${setting.key}</td>
				<td>
					<g:editInPlace id="${setting.key}"
						url="[action: 'editField', id: '${setting.key}']"
						rows="1"
						cols= "10"
						paramName="value">${setting.value.encodeAsHTML()}
					</g:editInPlace>
				</td>
				<td><g:remoteLink before="if(!confirm('Are you sure?')) return false" action="deleteKey" params="${[offset: params.offset, max: params.max, keyToDelete: setting.key]}" update="[success:'body', failure:'body']">remove</g:remoteLink></td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
   		<g:remotePaginate total="${settingsTotal}" />
	</div>
</div>

<div>
	<h1>Further options:</h1>
	<p style="padding-left:20px;"><g:remoteLink before="if(!confirm('Are you sure?')) return false" action="reindex" update="body" params="${[bodyOnly:true]}">Lucene: Recreate Index</g:remoteLink></p>
</div>

</div>
<script>
YAHOO.util.Event.onDOMReady(function() {
	  GRAILSUI.settingsAutoComplete.itemSelectEvent.subscribe(function(type, args) {
	     ${remoteFunction(action:"updateShowResult", params: '\'key=\'+GRAILSUI.settingsAutoComplete.getInputEl().getValue()', update: [success:'showResult',failure:'showResult'])};
	  });
	});
</script>	
</body>
</html>