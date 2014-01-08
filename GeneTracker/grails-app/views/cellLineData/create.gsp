
<%@ page import="org.openlab.genetracker.CellLineData" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
        <g:set var="entityName" value="${message(code: 'cellLineData.label', default: 'CellLineData')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cellLineDataInstance}">
            <div class="errors">
                <g:renderErrors bean="${cellLineDataInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <g:hiddenField name="bodyOnly" value="true"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cellLine"><g:message code="cellLineData.cellLine.label" default="Cell Line" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'cellLine', 'errors')}">
                                    <g:select class="select2" name="cellLine.id" from="${org.openlab.genetracker.CellLine.list(sort:'label')}" optionKey="id" value="${cellLineDataInstance?.cellLine?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="acceptor"><g:message code="cellLineData.acceptor.label" default="Acceptor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'acceptor', 'errors')}">
                                    <g:select class="select2" name="acceptor.id" from="${org.openlab.genetracker.vector.Acceptor.list(sort:'label')}" optionKey="id" noSelection="['':'']" value="${cellLineDataInstance?.acceptor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="firstRecombinant"><g:message code="cellLineData.firstRecombinant.label" default="First Vector Combination" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'firstRecombinant', 'errors')}">
                                    <g:if test="${cellLineDataInstance.firstRecombinant}">
                                        <g:hiddenField name="firstRecombinant.id" value="${cellLineDataInstance.firstRecombinant.id}"/>
                                        ${cellLineDataInstance.firstRecombinant}
                                    </g:if>
                                    <g:else>
                                        <g:select class="select2" noSelection="['':'']" onChange="${remoteFunction(action: 'updateFirstVector', update: 'firstVectorSpan', params:'\'firstGene=\'+this.value')}" name="firstRecombinantGene.id" from="${org.openlab.genetracker.Gene.list(sort:'name')}" optionKey="id" />
                                        <span id="firstVectorSpan">${cellLineDataInstance?.firstRecombinant?.vector?:"Select a gene"}</span>
                                    </g:else>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="secondRecombinant"><g:message code="cellLineData.secondRecombinant.label" default="Second Vector Combination" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'secondRecombinant', 'errors')}">
                                    <g:if test="${cellLineDataInstance.secondRecombinant}">
                                        <g:hiddenField name="firstRecombinant.id" value="${cellLineDataInstance.secondRecombinant.id}"/>
                                        ${cellLineDataInstance.secondRecombinant}
                                    </g:if>
                                    <g:else>
                                        <g:select class="select2" noSelection="['':'']" onChange="${remoteFunction(action: 'updateSecondVector', update: 'secondVectorSpan', params:'\'secondGene=\'+this.value')}" name="firstRecombinantGene.id" from="${org.openlab.genetracker.Gene.list(sort:'name')}" optionKey="id" value="${cellLineDataInstance?.secondRecombinant?.gene?.id}"  />
                                        <span id="secondVectorSpan">${cellLineDataInstance?.secondRecombinant?.vector?:"Select a gene"}</span>
                                    </g:else>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cultureMedia"><g:message code="cellLineData.cultureMedia.label" default="Culture Media" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'cultureMedia', 'errors')}">
                                    <g:select class="select2" name="cultureMedia.id" from="${org.openlab.genetracker.CultureMedia.list(sort:'label')}" optionKey="id" value="${cellLineDataInstance?.cultureMedia?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="plasmidNumber"><g:message code="cellLineData.plasmidNumber.label" default="Plasmid Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'plasmidNumber', 'errors')}">
                                    <g:textField name="plasmidNumber" value="${cellLineDataInstance?.plasmidNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="colonyNumber"><g:message code="cellLineData.colonyNumber.label" default="Colony Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'colonyNumber', 'errors')}">
                                    <g:textField name="colonyNumber" value="${cellLineDataInstance?.colonyNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="notes"><g:message code="cellLineData.notes.label" default="Notes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'notes', 'errors')}">
                                    <g:textField name="notes" value="${cellLineDataInstance?.notes}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
                    	<g:submitToRemote class="save" action="save" update="[success:'body',failure:'error']" value="${message(code: 'default.button.list.label', default: 'Create')}" />
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>









