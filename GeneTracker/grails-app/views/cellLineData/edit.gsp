
<%@ page import="org.openlab.genetracker.CellLineData" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
        <g:set var="entityName" value="${message(code: 'cellLineData.label', default: 'CellLineData')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cellLineDataInstance}">
            <div class="errors">
                <g:renderErrors bean="${cellLineDataInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${cellLineDataInstance?.id}" />
                <g:hiddenField name="version" value="${cellLineDataInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                   		
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cellLine"><g:message code="cellLineData.cellLine.label" default="Cell Line" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'cellLine', 'errors')}">
                                    <g:select name="cellLine.id" from="${org.openlab.genetracker.CellLine.list()}" optionKey="id" value="${cellLineDataInstance?.cellLine?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="acceptor"><g:message code="cellLineData.acceptor.label" default="Acceptor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'acceptor', 'errors')}">
                                    <g:select name="acceptor.id" from="${org.openlab.genetracker.vector.Acceptor.list()}" optionKey="id" value="${cellLineDataInstance?.acceptor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="firstRecombinant"><g:message code="cellLineData.firstRecombinant.label" default="First Vector Combination" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'firstRecombinant', 'errors')}">
                                    <g:select noSelection="['':'']" onChange="${remoteFunction(action: 'updateFirstVector', update: 'firstVectorSpan', params:'\'firstGene=\'+this.value')}" name="firstRecombinantGene.id" from="${org.openlab.genetracker.Gene.list()}" optionKey="id" value="${cellLineDataInstance?.firstRecombinant?.genes?.toList()?.get(0)?.id}"  />
                                    <span id="firstVectorSpan">${cellLineDataInstance?.firstRecombinant?.vector?:"Select a gene"}</span>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="secondRecombinant"><g:message code="cellLineData.secondRecombinant.label" default="Second Vector Combination" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'secondRecombinant', 'errors')}">
                                    <g:select noSelection="['':'']" onChange="${remoteFunction(action: 'updateSecondVector', update: 'secondVectorSpan', params:'\'secondGene=\'+this.value')}" name="firstRecombinantGene.id" from="${org.openlab.genetracker.Gene.list()}" optionKey="id" value="${cellLineDataInstance?.secondRecombinant?.genes?.toList()?.get(0)?.id}"  />
                                    <span id="secondVectorSpan">${cellLineDataInstance?.secondRecombinant?.vector?:"Select a gene"}</span>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cultureMedia"><g:message code="cellLineData.cultureMedia.label" default="Culture Media" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'cultureMedia', 'errors')}">
                                    <g:select name="cultureMedia.id" from="${org.openlab.genetracker.CultureMedia.list()}" optionKey="id" value="${cellLineDataInstance?.cultureMedia?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mediumAdditives"><g:message code="cellLineData.mediumAdditives.label" default="Medium Additives" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cellLineDataInstance, field: 'mediumAdditives', 'errors')}">
                                    <g:select name="mediumAdditives" from="${org.openlab.genetracker.MediumAdditive.list()}" multiple="yes" optionKey="id" size="5" value="${cellLineDataInstance?.mediumAdditives}" />
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
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
