<html>
    <head>
        <title><g:layoutTitle default="OpenLaboratoryFramework" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
	<body>
	  <div id="spinner" class="spinner" style="display:none;">
	    <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
	  </div> 
	  <g:render template="/layouts/header"/>        
	  <div class="maincontent">
	  <div class="leftnavbar" id ="leftnavbar"><g:include controller=${params.controller} action="xmlTree"/></div>
	  <div class="body"><g:layoutBody /></div>
	  <div class="sidebarcontent" id="addins"><addin:layoutAddins controller=${params.controller}/></div>
	  </div>
	</body>

</html>