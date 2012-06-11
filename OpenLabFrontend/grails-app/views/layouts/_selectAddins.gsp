<table>
<g:each status="i" in="${currentAddins}" var="addin">
	<tr>
		<td>
			Addin in slot ${i+1}: 
		</td>
		<td>
			<g:select noSelection="['':'']" from="${allAddins}" value="${addin}" onChange="${remoteFunction(update:'msg', id: i+1, params: '\'newValue=\'+this.value', controller: 'usersettings', action: 'changeAddin') }"></g:select>
		</td>
	</tr>
</g:each>
</table>
