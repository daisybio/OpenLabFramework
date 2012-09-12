<g:javascript library="prototype" />
<table>
    <thead><tr><th>TaqMan Sets</th><th>Create Set From TaqManResults</th></tr></thead>
    <tr>
        <td rowspan="2">
            <g:select name="taqManSetSelection"
                      onchange="new Ajax.Updater({success:'taqManSetDetails',failure:'body'},'${g.createLink(controller:"taqManSet", action:"showDetails")}',{asynchronous:true,evalScripts:true,parameters: 'id='+ this.value});"
                      optionKey="id" from="${taqManSets}" size="20" multiple="true"/></td>
        <td>Name for new set: <g:textField name="newSet" value=""></g:textField></td></tr>
     <tr><td>
         <g:select name="taqManResultSelection"
                   onchange="new Ajax.Updater({success:'taqManResultDetails',failure:'body'},'${g.createLink(controller:"taqManResult", action:"showDetails")}',{asynchronous:true,evalScripts:true,parameters: 'id='+ this.value});"
                   optionKey="id" from="${taqManResults}" size="18" multiple="true"/></td>
    </tr>

</table><br/>


<div id="taqManSetDetails"></div><br/>
<div id="taqManResultDetails"></div>

		
