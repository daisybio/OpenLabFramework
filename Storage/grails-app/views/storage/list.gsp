<html>
    <head>
        <title>Storage Modul</title>
		<meta name="layout" content="main" />
	</head>
<body>
<div class="body" style="width:100%;">
	<h1>Storage Management</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
	    </g:if>
        <g:hasErrors bean="${propertyName}">
          <div class="errors">
        	<g:renderErrors bean="{${propertyName}" as="list" />
		  </div>
         </g:hasErrors>
      	
      	<div style="float:right; width:80%; padding-right:15px;" id="boxView">
      		<g:if test="${params.id}">
				<g:include controller="box" action="showBox" id="${params.id}"/>
      		</g:if>
      	</div>
      	
		<div style="float:left; width:15%; text-align: center; height: 600px; overflow:scroll;">
			<g:include action="showTree" id="${params.id}"/>
	 	</div>
	 	
	 	<div id="storageEditPanel"></div>
</div>
</body>        
         