			<%@ page import="org.openlab.genetracker.CellLineData" %>
          	
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.cellLine.label" default="Cell Line" /></td>
                            
                            <td valing="top" class="value">
								<g:editCollectionInPlace id="cellLine"
									url="[action: 'editCollectionField', id:cellLineDataInstance.id]"
									paramName="cellLine"
									className="${CellLineData}"
									referencedClassName="org.openlab.genetracker.CellLine">
								${cellLineDataInstance?.cellLine?.encodeAsHTML()}
								</g:editCollectionInPlace>
                           	</td>
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.acceptor.label" default="Acceptor" /></td>
                            
                            <td valing="top" class="value">
								<g:editCollectionInPlace id="acceptor"
									url="[action: 'editCollectionField', id:cellLineDataInstance.id]"
									paramName="acceptor"
									className="${CellLineData}"
									referencedClassName="org.openlab.genetracker.vector.Acceptor">
								${cellLineDataInstance?.acceptor?.encodeAsHTML()}
								</g:editCollectionInPlace>
                           	</td>
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.firstRecombinant.label" default="First Vector Combination" /></td>
                            
                            <td valing="top" class="value">
								<g:editCollectionInPlace id="firstRecombinant"
									url="[action: 'editCollectionField', id:cellLineDataInstance.id]"
									restriction="[subtype: 'type', type: 'vector', value: 'Integration (First)']"
									paramName="firstRecombinant"
									className="${CellLineData}"
									referencedClassName="org.openlab.genetracker.Recombinant">
								${cellLineDataInstance?.firstRecombinant?.encodeAsHTML()}
								</g:editCollectionInPlace>
                           	</td>
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.secondRecombinant.label" default="Second Vector Combination" /></td>
                            
                            <td valing="top" class="value">
								<g:editCollectionInPlace id="secondRecombinant"
									url="[action: 'editCollectionField', id:cellLineDataInstance.id]"
									restriction="[subtype: 'type', type: 'vector', value: 'Integration (Second)']"
									paramName="secondRecombinant"
									className="${CellLineData}"
									referencedClassName="org.openlab.genetracker.Recombinant">
								${cellLineDataInstance?.secondRecombinant?.encodeAsHTML()}
								</g:editCollectionInPlace>
                           	</td>
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.cultureMedia.label" default="Culture Media" /></td>
                            
                            <td valing="top" class="value">
								<g:editCollectionInPlace id="cultureMedia"
									url="[action: 'editCollectionField', id:cellLineDataInstance.id]"
									paramName="cultureMedia"
									className="${CellLineData}"
									referencedClassName="org.openlab.genetracker.CultureMedia">
								${cellLineDataInstance?.cultureMedia?.encodeAsHTML()}
								</g:editCollectionInPlace>
                           	</td>
                            
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.mediumAdditives.label" default="Goodies" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${cellLineDataInstance.mediumAdditives}" var="g">
                                    <li><g:remoteLink controller="mediumAdditive" action="show" id="${g.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">${g?.encodeAsHTML()}</g:remoteLink></li>
                                </g:each>
                                </ul>
                                <modalbox:createLink params="['property':'mediumAdditives','className':'org.openlab.genetracker.MediumAdditive', 'controllerName':'MediumAdditive']" id="${cellLineDataInstance.id}" action="editMany" title="Edit mediumAdditives" width="500"><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></modalbox:createLink>
                                <!-- <span class="simpleButton"><g:remoteLink controller="mediumAdditive" action="create" update="[success: 'body', failure: 'body']"><img src="${resource(dir:'images/skin',file:'olf_add.png')}" /></g:remoteLink></span> --> 
                            </td>
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.plasmidNumber.label" default="Plasmid Number" /></td>
                            
                            <!-- Replaced the original fieldValue td with one incorporating in-line editing -->
                            <td valign="top" class="value">
                           		<g:editInPlace id="plasmidNumber"
									url="[action: 'editField', id:cellLineDataInstance.id]"
									rows="1"
									paramName="plasmidNumber">
										<g:if test="${fieldValue(bean: cellLineDataInstance, field: 'plasmidNumber')}">${fieldValue(bean: cellLineDataInstance, field: 'plasmidNumber')}
										</g:if>
										<g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
								</g:editInPlace>
							</td>
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.colonyNumber.label" default="Colony Number" /></td>
                            
                            <!-- Replaced the original fieldValue td with one incorporating in-line editing -->
                            <td valign="top" class="value">
                           		<g:editInPlace id="colonyNumber"
									url="[action: 'editField', id:cellLineDataInstance.id]"
									rows="1"
									paramName="colonyNumber">
										<g:if test="${fieldValue(bean: cellLineDataInstance, field: 'colonyNumber')}">${fieldValue(bean: cellLineDataInstance, field: 'colonyNumber')}
										</g:if>
										<g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
								</g:editInPlace>
							</td>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.notes.label" default="Notes" /></td>
                            
                            <!-- Replaced the original fieldValue td with one incorporating in-line editing -->
                            <td valign="top" class="value">
                           		<g:editInPlace id="notes"
									url="[action: 'editField', id:cellLineDataInstance.id]"
									rows="1"
									paramName="notes">
										<g:if test="${fieldValue(bean: cellLineDataInstance, field: 'notes')}">${fieldValue(bean: cellLineDataInstance, field: 'notes')}
										</g:if>
										<g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
								</g:editInPlace>
							</td>
                           
                       <tr class="prop">
                            <td valign="top" class="name"><g:message code="cellLineData.projects.label" default="Projects" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${cellLineDataInstance.projects}" var="g">
                                    <li><g:remoteLink controller="project" action="show" id="${g.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">${g?.encodeAsHTML()}</g:remoteLink></li>
                                </g:each>
                                </ul>
                                <modalbox:createLink params="['property':'projects','className':'org.openlab.main.Project', 'controllerName':'Project']" id="${cellLineDataInstance.id}" action="editMany" title="Edit projects" width="500"><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></modalbox:createLink>
                                <!-- <span class="simpleButton"><g:remoteLink controller="mediumAdditive" action="create" update="[success: 'body', failure: 'body']"><img src="${resource(dir:'images/skin',file:'olf_add.png')}" /></g:remoteLink></span> --> 
                            </td> 
               	</tr></tbody></table>
