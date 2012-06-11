<div id="header">
  <g:buildMenu></g:buildMenu>
  <g:link class="header-main" controller="dashboard">
  <div style="float:left; width:180px;"><g:loadLogo/></div>
  </g:link>
  <span class="title">OpenLabFramework</span>
  
  <div style="float:right; width:300px; margin-top: 0px;" id="loginHeader">
    <g:form controller="fullSearch" action="index">
    <g:hiddenField name="bodyOnly" value="true"/>
    <g:hiddenField name="suggestQuery" value="true"/>
    <table>
    	<tr>
    		<th colspan=2><g:remoteLink controller="fullSearch" action="index" params="['bodyOnly':'true']" update="['success':'body', 'failure':'body']">Search</g:remoteLink></th>
   		</tr>
   		<tr>
   			<td valign="center">
				<gui:autoComplete
		   	        minQueryLength="3"
			        queryDelay="0.5"
		        	id="q"
		        	resultName="results"
		        	labelField="label"
		        	idField="id"
		        	controller="quickSearch"
		        	action="searchResultsAsJSON"
				/>
   			</td>
   			<td align="right" width="30">
   				<g:submitToRemote name='fullSearch' value="Search" method="get" controller="fullSearch" action="index" update="['success':'body', 'failure':'body']"/>
   			</td>
   		</tr>
   	</table>
	</g:form>
		
    <div style="margin-top:25px;" align="right">
    	<g:loginControl />
    </div>
  </div>
</div>
	<div id="loading" style="display: none;">
	Loading...
	</div>
	<div id="blocker" style="display:none;"/> 
<script>
YAHOO.util.Event.onDOMReady(function() {
	  GRAILSUI.q.itemSelectEvent.subscribe(function(oSelf , elItem , oData) {
	     ${remoteFunction(controller:"quickSearch", action:"showResult", params: '\'name=\'+elItem[2][0]+\'&id=\'+elItem[2][1]', update: [success:'body',failure:'body'])};
	  });

	  GRAILSUI.q.textboxFocusEvent.subscribe(function(oSelf, elItem, oData) {
		  GRAILSUI.q.getInputEl().value = '';
	  });
	});
</script>