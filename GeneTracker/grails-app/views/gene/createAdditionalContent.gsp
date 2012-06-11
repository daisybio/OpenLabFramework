<div style="float:right; width:30%; padding:30px;" id="createAdditionalContent">
	<g:form>
	<table>
		<thead>
		<tr>
			<th colspan="2">Populate fields with NCBI</th>
		</tr>
		</thead>
		<tbody>
		<tr>
				<td><input onfocus="if(this.value=='accession number'){this.value='';}" type="text" value="accession number" name="accessionNr"></input>
					<g:hiddenField name="bodyOnly" value="true" />
				</td>
				<td><g:submitToRemote action="createWithNcbi" value="Populate" update="[success:'body', failure:'body']"></g:submitToRemote></td>
		</tr>
		</tbody>
	</table>
	</g:form>	
</div>