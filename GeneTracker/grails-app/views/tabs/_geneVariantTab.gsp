Filter: <g:select name="typeSelect" from="${types}" noSelection="['':'All']" onChange="${remoteFunction(controller:'gene', action:'updateVariantList', update:'variantList', id: gene.id, params:'\'filterParam=\'+this.value')}"></g:select><br/></br>
            <div id="variantList" class="list">
				<g:render plugin="gene-tracker" template="/layouts/variantList" model="['geneVariants':geneVariants]"></g:render>
			</div>

