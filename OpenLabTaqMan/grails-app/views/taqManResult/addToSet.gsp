<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <title>Add TaqManResult to set</title>
</head>

<body>
<g:if test="${message}">
    <div class="message" style="border: 1px solid grey; padding:15px;">${message}</div>
</g:if>
<div style="margin-left: 10px;">

    <h1>Before you can analyse you TaqMan results you should add them to a set</h1>   <br/>

    <b>Add to existing sets:</b>
    <g:form id="setSelection" action="saveToSet">
        <g:hiddenField name="taqManResult" value="${taqManResultInstance.id}"/>
        <g:select name="setSelect" optionKey="id" from="${taqManSets}"></g:select>
        <input type="submit" name="add" value="Add to set"/>
    </g:form>

    <br/>  <br/>
    <b>Or create a new set:</b>
    <g:form id="newSet" action="createSet">
        <table style="width:500px;">
            <g:hiddenField name="taqManResult" value="${taqManResultInstance.id}"/>
            <tr><td>Name of the set:</td><td><g:textField name="nameOfSet"/></td></tr>

            <tr>
                <td>Also add the following selected reference plates:</td>
                <td><g:select name="referenceSelect" optionKey="id" multiple="true" from="${referenceResults}"/></td>
            </tr>
        </table>
        <input type="submit" name="add" value="Create new set"/>
    </g:form>
</div>
</body>
</html>