		<g:if test="${treedata}">
	 		<g:if test="${treeInTab}">
		 		<richui:treeView id="storagetree" showRoot="true" xml="${treedata}" onLabelClick="
		 		if(node.node.depth==3){
		 			${remoteFunction(controller: 'box', action: 'showBoxInTab',
		 					onSuccess: 'javascript:olfEvHandler.boxViewChangedEvent.fire(node.node.additionalId)', 		 				
		 					before: '\$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')',
		 					id: dataObjId,
		 					params: '\'boxId=\'+node.node.additionalId', 
			 				update:[success:'boxView', failure:'yui-main'])}
				}"/>	
			</g:if>
			<g:else>
				<richui:treeView id="storagetree" showRoot="true" xml="${treedata}" onLabelClick="
		 		if(node.node.depth==1){
		 			${remoteFunction(controller: 'storageType', params: '\'bodyOnly=true&id=\'+node.node.additionalId', action: 'show', update:[success: 'boxView', failure: 'boxView'])}	
		 		}
		 		if(node.node.depth==2){
		 			${remoteFunction(controller: 'compartment', params: '\'bodyOnly=true&id=\'+node.node.additionalId', action: 'show', update:[success: 'boxView', failure: 'boxView'])}	
		 		}
		 		if(node.node.depth==3){
		 			${remoteFunction(controller: controller, action: action, 
		 					before: '\$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')',
		 					params: '\'id=\'+node.node.additionalId', 
			 				update:[success:'boxView', failure:'yui-main'])}
				}"/>		
			</g:else>
			<script type="text/javascript">
			YAHOO.widget.TreeView.getTree('storagetree').expandAll();
			</script>
			<g:treeControls id="storagetree"></g:treeControls>
 		</g:if>