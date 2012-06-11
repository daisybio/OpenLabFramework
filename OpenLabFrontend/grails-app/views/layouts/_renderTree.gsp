<div id="projectTree">
	  <g:if test="${data}">
		<g:treeControls id="tree"></g:treeControls>
		<richui:treeView id="tree" showRoot="true" xml="${data}" onLabelClick="
			if(node.node.depth==1) ${remoteFunction(controller:'project', action:'list', params:[bodyOnly:true], update:[success:'body', failure:'yui-main'])};
			/*if(node.node.depth==2) ${remoteFunction(controller:'projectTree', action:'listSubsetOfProjects', params:'\'id=\'+node.node.additionalId+\'&bodyOnly=true\'', update:[success:'body', failure:'yui-main'])};*/
			if(node.node.depth==3) ${remoteFunction(controller:'project', action:'show', params:'\'id=\'+node.node.additionalId+\'&bodyOnly=true\'', update:[success:'body', failure:'yui-main'])};
			if(node.node.depth==4) ${remoteFunction(controller:'dataObject', action:'showSubClass', params: '\'id=\'+node.node.additionalId', update:[success:'body', failure:'yui-main'])};"/>
		<g:treeControls id="tree"></g:treeControls>
	  </g:if>


   <!-- select appropriate node in project tree -->
   	<script type="text/javascript">
	var selectedNode = YAHOO.widget.TreeView.getTree('tree').getNodeByProperty('selected',true);
	if (selectedNode){
		selectedNode.focus();
	}
	</script>

</div>
