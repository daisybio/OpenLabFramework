<%@ page import="org.openlab.main.Project" %>
<!doctype html>
<html>
<head>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly ? 'body' : 'main'}"/>
    <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <r:require module="export"/>
</head>

<body>
<a href="#list-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" update="body" class="create" action="create"><g:message
                code="default.new.label" args="[entityName]"/></g:remoteLink></li>
    </ul>
</div>

<div id="list-project" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <div id="filter" class="boxShadow">
        <h2>Filter options:</h2>

        <div style="padding:15px;"/>
        <g:formRemote update="body" name="filterList" url="[controller: 'project', action: 'list']">
            <g:hiddenField name="bodyOnly" value="${true}"/>
            Results per page: <g:select name="max" value="${params.max ?: 10}" from="${10..projectInstanceTotal}"
                                        class="range"/>

            Creator: <g:select name="creatorFilter" from="${org.openlab.security.User.list().collect {it.username}}"
                               value="${params.creatorFilter ?: ''}" noSelection="['': '']"/>


            Last Modifier: <g:select name="lastModifierFilter"
                                     from="${org.openlab.security.User.list().collect {it.username}}"
                                     value="${params.lastModifierFilter ?: ''}" noSelection="['': '']"/>


            <g:submitButton name="Filter"/>
        </g:formRemote>
    </div>
</div>

<table>
    <thead>
    <tr>

        <th><g:message code="project.creator.label" default="Creator"/></th>

        <th><g:message code="project.lastModifier.label" default="Last Modifier"/></th>


        <g:remoteSortableColumn property="lastUpdate" params="${params}"
                                title="${message(code: 'project.lastUpdate.label', default: 'Last Update')}"/>


        <g:remoteSortableColumn property="dateCreated" params="${params}"
                                title="${message(code: 'project.dateCreated.label', default: 'Date Created')}"/>


        <g:remoteSortableColumn property="name" params="${params}"
                                title="${message(code: 'project.name.label', default: 'Name')}"/>


        <g:remoteSortableColumn property="description" params="${params}"
                                title="${message(code: 'project.description.label', default: 'Description')}"/>

        <th/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${projectInstanceList}" status="i" var="projectInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

            <td>${fieldValue(bean: projectInstance, field: "creator")}</td>

            <td>${fieldValue(bean: projectInstance, field: "lastModifier")}</td>

            <td><g:formatDate type="date" date="${projectInstance.lastUpdate}"/></td>

            <td><g:formatDate type="date" date="${projectInstance.dateCreated}"/></td>

            <td><g:editInPlace id="name_${projectInstance.id}"
                               url="[action: 'editField', id: projectInstance.id]"
                               rows="1"
                               cols="10"
                               paramName="name">${fieldValue(bean: projectInstance, field: "name")}
            </g:editInPlace>
            </td>

            <td><g:editInPlace id="description_${projectInstance.id}"
                               url="[action: 'editField', id: projectInstance.id]"
                               rows="1"
                               cols="10"
                               paramName="description">${fieldValue(bean: projectInstance, field: "description")}
            </g:editInPlace>
            </td>

            <td><g:remoteLink params="${[bodyOnly: true]}" action="show" id="${projectInstance.id}"
                              update="body">show</g:remoteLink></td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:remotePaginate total="${projectInstanceTotal}" params="${params}"/>
</div>
<export:formats params="${params}"/>
</div>
<script type="text/javascript">
    olfEvHandler.bodyContentChangedEvent.fire("${projectInstance?.toString()}");
</script>
</body>
</html>
