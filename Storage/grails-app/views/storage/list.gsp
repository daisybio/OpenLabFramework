<html>
    <head>
        <title>Storage Management</title>
		<meta name="layout" content="${params.bodyOnly?'body':'main'}" />
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
      	
      	<div style="float:right; width:75%; padding-right:15px;" id="boxView">
      		<g:if test="${params.id}">
				<g:include controller="box" action="showBox" id="${params.id}"/>
      		</g:if>
      	</div>
      	
		<div id="storageTree" style="float:left; width:20%; height: 100%; overflow:scroll;"/>
</div>

<g:javascript src="/jquery-1.8.2.min.js"/>
<g:javascript src="/jquery.jstree.js"/>

<g:render template="/layouts/storageTree"/>

</body>
</html>
         