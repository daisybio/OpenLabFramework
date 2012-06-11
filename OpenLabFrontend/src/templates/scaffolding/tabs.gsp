<div id="tabs" style="min-width:550px; width:100%; margin-top:20px;">  
    <gui:tabView id='tabView'>
		<g:renderInterestedModules id='${propertyName}.id' domainClass='${domainClass.propertyName}'></g:renderInterestedModules>
	</gui:tabView>
</div>
<g:javascript>
	if(GRAILSUI.tabView.get('tabs').size() == 0){
		document.getElementById('tabs').style.display = "none";
	}
	else GRAILSUI.tabView.set('activeIndex', 0);
</g:javascript> 
