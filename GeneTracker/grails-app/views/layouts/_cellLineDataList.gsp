                <table>
                    <thead>
                        <tr>
                            <th>CellLine</th>
                            <th>Acceptor</th>
                            <th>First Recombinant</th>
                            <th>Second Recombinant
                            <th>Colony Number</th>
                            <th>Last Update</th>
                 		    <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${cellLineDataList}" status="i" var="cellLineDataInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${cellLineDataInstance.cellLine}</td>
                            <td>${cellLineDataInstance.acceptor}</td>
                            <td>${cellLineDataInstance.firstRecombinant}</td>
                            <td>${cellLineDataInstance.secondRecombinant}</td>
                            <td>${cellLineDataInstance.colonyNumber}</td>
                            <td><g:formatDate type="date" date="${cellLineDataInstance.lastUpdate}" /></td>
           					<td class="actionButtons">
								<span class="actionButton">
									<g:remoteLink controller="cellLineData" action="show" params="[bodyOnly: true]" id="${cellLineDataInstance.id}" update="[success:'body',failure:'body']">Show</g:remoteLink>
								</span>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>