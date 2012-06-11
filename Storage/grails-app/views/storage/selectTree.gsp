<g:if test="${treedata}">
 		<richui:treeView id="storagetree" showRoot="true" xml="${treedata}" onLabelClick="
 		if(node.node.depth==3){
	 		${remoteFunction(controller:'box', action:'showBox', 
					before: '\$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')',
 					params: '\'id=\'+node.node.additionalId', 
	 				update:[success:'boxView', failure:'yui-main'])}
		}"/>
</g:if>
<script type="text/javascript">"
var oElement = document.getElementById("storagetree");
function fnCallback(e) { alert("animComplete"); }
YAHOO.util.Event.addListener(oElement, "click", fnCallback);
</script>