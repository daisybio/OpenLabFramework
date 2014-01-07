<g:if test="${!geneInstance.wildTypeGene}">

    <li>
        <a target="_blank" href="http://www.ncbi.nlm.nih.gov/gene?cmd=search&term=${geneInstance.name}"><img src="http://static.pubmed.gov/portal/portal3rc.fcgi/3938510/img/28977" alt="Find gene on NCBI"></a>
    </li>
    <li>
		<g:remoteLink action="createVariant" params="[type:'Mutant', typeShort:'M']" controller="gene" id="${geneInstance.id}" update="[success: 'body', failure:'body']">
			<img src=${createLinkTo(dir:'images/skin',file:'olf_m.png')} />
			Add Mutated Variant
		</g:remoteLink>
	</li>
	<li>
		<g:remoteLink action="createVariant" params="[type:'Knockdown', typeShort:'K']" controller="gene" id="${geneInstance.id}" update="[success: 'body', failure:'body']">
			<img src=${createLinkTo(dir:'images/skin',file:'olf_k.png')} />
			Add Knockdown Construct
		</g:remoteLink>
	</li>
	<li>
		<g:remoteLink action="createVariant" params="[type:'Promotor', typeShort:'P']" controller="gene" id="${geneInstance.id}" update="[success: 'body', failure:'body']">
			<img src=${createLinkTo(dir:'images/skin',file:'olf_p.png')} />
			Add Promotor
		</g:remoteLink>
	</li>
	<li>
		<g:remoteLink action="createVariant" params="[type:'Fragment', typeShort:'F']" controller="gene" id="${geneInstance.id}" update="[success: 'body', failure:'body']">
			<img src=${createLinkTo(dir:'images/skin',file:'olf_f.png')} />
			Add Gene Fragment
		</g:remoteLink>
	</li>
</g:if>
