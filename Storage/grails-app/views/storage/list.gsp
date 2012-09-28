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
      	
      	<div style="float:right; width:80%; padding-right:15px;" id="boxView">
      		<g:if test="${params.id}">
				<g:include controller="box" action="showBox" id="${params.id}"/>
      		</g:if>
      	</div>
      	
		<div id="storageTree" style="float:left; width:20%; height: 100%; overflow:scroll;"/>
	 	<div id="storageEditPanel"></div>
</div>

<g:javascript src="/jquery-1.8.2.min.js"/>
<g:javascript src="/jquery.jstree.js"/>

<script type="text/javascript">
    jQuery.noConflict();

    var myStorageTree = jQuery("#storageTree").jstree({
        "json_data": {
            "ajax": {
                "url":  "<g:createLink controller="storage" action="treeDataAsJSON"/>",
                "data" : function(n) { return { id: n.attr? n.attr("id") : 0, nodeType: n.attr? n.attr("nodeType") : "root" }; }
            }
        },
        "themes" : {
            "theme" : "default",
            "url" : "<g:resource dir="themes" file="default/style.css"/>",
            "icons" : false
        },
        "types" : {
            "valid_children" : ["location"],
            "types" : {
                "location" : {
                  "valid_children": ["storageType"]
                },
                "storageType" : {
                    "hover_node": false,
                    "valid_children": ["compartment"]
                },
                "compartment" : {
                    "valid_children": ["box"]
                } ,
                "box": {

                }
            }
        },
        "plugins": ["themes", "ui", "json_data", "types"]
    });

    myStorageTree.bind("select_node.jstree", function(event, data){
        if(data.inst.is_leaf(data.args[0])){
        ${remoteFunction(action: 'showBox', controller: 'box', update: 'storageEditPanel', params: '\'id=\'+data.rslt.obj.attr("id")')}
        }
    });
</script>

</body>
</html>
         