<r:script>
<g:remoteFunction controller="storage" action="selectTree" update="[success: 'storageList']" onComplete="Modalbox.resizeToContent();"/>
</r:script>
<div id="storageList"/>