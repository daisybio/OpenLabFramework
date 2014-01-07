                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Last Update</th>
                 		    <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${geneVariants}" status="i" var="variant">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${variant}</td>
                            <td>${variant.geneType}</td>
                            <td><g:formatDate date="${variant.lastUpdate}" /></td>
           					<td class="actionButtons">
								<span class="actionButton">
									<g:remoteLink controller="gene" action="show" params="[bodyOnly: true]" id="${variant.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
								</span>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>