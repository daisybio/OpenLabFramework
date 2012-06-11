<table>
    <thead><tr><th>TaqMan Sets</th><th>Create Set From TaqManResults</th></tr></thead>
    <tr>
        <td rowspan="2"><g:select name="taqManSetSelection"
                      onchange="${remoteFunction(controller: 'taqManSet', action: 'showDetails', update: [success: 'taqManSetDetails', failure: 'body'], params: '\'id=\'+this.value')}"
                      optionKey="id" from="${taqManSets}" size="20" multiple="true"></g:select></td>
        <td>Name for new set: <g:textField name="newSet" value=""></g:textField></td></tr>
     <tr><td>
            <g:select name="taqManResultSelection" size="18" optionKey="id" from="${taqManResults}"
                      multiple="true"
                      onchange="${remoteFunction(controller: 'taqManResult', action: 'showDetails', update: [success: 'taqManResultDetails', failure: 'body'], params: '\'id=\'+this.value')}" >
                      </g:select></td>
    </tr>

</table><br/>


<div id="taqManSetDetails"></div><br/>
<div id="taqManResultDetails"></div>

		
