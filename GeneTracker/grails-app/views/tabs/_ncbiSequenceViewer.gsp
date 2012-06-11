<g:if test="${originalGene && accessionNumber}">
	<gui:tab label="NCBI Sequence Viewer" cacheData="true">
		<g:ncbiSequenceViewer geneId="${geneId}"/>
	</gui:tab>
</g:if>