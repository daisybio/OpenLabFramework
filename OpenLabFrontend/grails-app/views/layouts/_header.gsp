<div id="header">
  <mygui:buildMenu/>
  <g:link class="header-main" controller="dashboard">
  <div style="float:left; width:180px;"><g:loadLogo/></div>
  </g:link>
  <span class="title" id="olfLogo">
    <g:link class="header-main" controller="dashboard">
        <img style="max-width: 100%;" src="<g:resource dir="images" file="olf_banner.png"/>" alt="OpenLabFramework"/>
    </g:link>
  </span>
  
  <span style="float:right; width: 470px; display: inline-block; margin-top: 20px;" id="loginHeader">
    <g:form controller="fullSearch" action="index">
    <g:hiddenField name="bodyOnly" value="true"/>
    <g:hiddenField name="suggestQuery" value="true"/>

   <span style="width:270px; float:left;">
    <span style="float:left; width:200px; display: inline"><gui:autoComplete
        minQueryLength="3"
        queryDelay="0.5"
        id="q"
        resultName="results"
        labelField="label"
        idField="id"
        controller="quickSearch"
        action="searchResultsAsJSON"
    /></span>
    <span style="float:right; width:50px;">
        <g:submitToRemote name='fullSearch' value="Search" method="get" controller="fullSearch" action="index" update="['success':'body', 'failure':'body']"/>
    </span>
   </span>

    </g:form>
    <span style="float:right;"><g:loginControl /></span>
  </span>
</div>
<r:script>
YAHOO.util.Event.onDOMReady(function() {
	  GRAILSUI.q.itemSelectEvent.subscribe(function(oSelf , elItem , oData) {
	     ${remoteFunction(controller:"quickSearch", action:"showResult", params: '\'name=\'+elItem[2][0]+\'&id=\'+elItem[2][1]', update: [success:'body',failure:'body'])};
	  });

	  GRAILSUI.q.textboxFocusEvent.subscribe(function(oSelf, elItem, oData) {
		  GRAILSUI.q.getInputEl().value = '';
	  });
	});
</r:script>
