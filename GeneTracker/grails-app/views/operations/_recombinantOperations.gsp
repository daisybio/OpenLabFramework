
<g:if test="${recombinantInstance.vector.type=="Integration (First)"}">
	<li>
		<g:remoteLink action="create" params="${['firstRecombinant.id': recombinantInstance.id, 'bodyOnly': true]}" controller="cellLineData" update="[success: 'body', failure:'body']">
			<img src=${createLinkTo(dir:'images/skin',file:'olf_m.png')} />
			Create new cell line recombinant
		</g:remoteLink>
	</li>
</g:if>

<g:if test="${recombinantInstance.vector.type=="Integration (Second)"}">
    <li>
        <g:remoteLink action="create" params="${['secondRecombinant.id': recombinantInstance.id, 'bodyOnly': true]}" controller="cellLineData" update="[success: 'body', failure:'body']">
            <img src=${createLinkTo(dir:'images/skin',file:'olf_m.png')} />
            Create new cell line recombinant
        </g:remoteLink>
    </li>
</g:if>

