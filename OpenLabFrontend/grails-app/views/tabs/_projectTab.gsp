     <div class="list">
         <table>
             <thead>
                 <tr>
                     <th>Name</th>
                     <th>Type</th>
                     <th>Last Updated</th>
          		    <th>&nbsp;</th>
                 </tr>
             </thead>
             <tbody>
             <g:each in="${dataObjects}" status="i" var="obj">
                 <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                     <td>${obj}</td>
                     <td>${obj.type}</td>
                     <td><g:formatDate date="${obj.lastUpdate}" /></td>
    				 <td class="actionButtons">
						<span class="actionButton">
							<g:remoteLink controller="dataObject" action="showSubClass" params="[bodyOnly: true]" id="${obj.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
						</span>
					 </td>
        	     </tr>
      	     </g:each>
             </tbody>
      </table>
	</div>
