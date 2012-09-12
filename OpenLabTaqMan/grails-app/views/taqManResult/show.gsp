<%@ page import="org.openlab.taqman.TaqManSet; org.openlab.taqman.TaqManResult" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <g:set var="entityName" value="${message(code: 'taqManResult.label', default: 'TaqManResult')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">

        <g:render template="showDetails" model="['taqManResultInstance': ${taqManResultInstance}"/>

        <tr class="prop">
            <g:render template="status" model="${[taqManResultInstance: taqManResultInstance]}"/>
        </tr>


        <tr class="prop">
            <g:if test="${taqManSets}">
            <h1>This dataset can be found in the following sets:</h1>
            <ul><g:each in="${taqManSets}" var="taqManSet">
              <li>${taqManSet}</li>
            </g:each></ul>
            </g:if>
            <g:include action="addToSet" id="${taqManResultInstance.id}"/>
        </tr>



    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
