<%@ page import="org.openlab.main.Project" %>
<!doctype html>
<html>
<head>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly ? 'body' : 'main'}"/>
    <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" class="list" action="list" update="body"><g:message
                code="default.list.label" args="[entityName]"/></g:remoteLink></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" class="create" action="create"><g:message
                code="default.new.label" args="[entityName]"/></g:remoteLink></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" class="delete" action="delete" id="${projectInstance?.id}"
                          update="body"
                          onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
            <g:message code='default.button.delete.label' default='Delete'/></g:remoteLink></li>
    </ul>
</div>
<g:render template="additionalBoxoes" id="${projectInstance?.id}"/>
<div id="show-project" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/> ${projectInstance}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list project">

        <li class="fieldcontain">
            <span id="name-label" class="property-label"><g:message code="project.name.label" default="Name"/></span>

            <span class="property-value" aria-labelledby="name-label">
                <g:set var="myInList" value="${projectInstance?.constraints.name.inList}"/>
                <g:if test="${myInList}">
                    <g:select from="${myInList}" name="nameSelect"
                              value="${fieldValue(bean: projectInstance, field: 'name')}"
                              onChange="${g.remoteFunction(action: 'editInList', id: projectInstance?.id, params: '"name=" + this.value', onFailure: 'alert("Could not save property change");')}"/>
                </g:if>
                <g:else>
                    <g:editInPlace id="name"
                                   url="[action: 'editField', id: projectInstance.id]"
                                   rows="1"
                                   paramName="name">
                        <g:if test="${fieldValue(bean: projectInstance, field: 'name')}">
                            ${fieldValue(bean: projectInstance, field: 'name')}
                        </g:if>
                        <g:else><img src="${resource(dir: 'images/skin', file: 'olf_tool_small.png')}"/></g:else>
                    </g:editInPlace>
                </g:else>
            </span>

        </li>



        <li class="fieldcontain">
            <span id="description-label" class="property-label"><g:message code="project.description.label"
                                                                           default="Description"/></span>

            <span class="property-value" aria-labelledby="description-label">
                <g:set var="myInList" value="${projectInstance?.constraints.description.inList}"/>
                <g:if test="${myInList}">
                    <g:select from="${myInList}" name="descriptionSelect"
                              value="${fieldValue(bean: projectInstance, field: 'description')}"
                              onChange="${g.remoteFunction(action: 'editInList', id: projectInstance?.id, params: '"description=" + this.value', onFailure: 'alert("Could not save property change");')}"/>
                </g:if>
                <g:else>
                    <g:editInPlace id="description"
                                   url="[action: 'editField', id: projectInstance.id]"
                                   rows="1"
                                   paramName="description">
                        <g:if test="${fieldValue(bean: projectInstance, field: 'description')}">
                            ${fieldValue(bean: projectInstance, field: 'description')}
                        </g:if>
                        <g:else><img src="${resource(dir: 'images/skin', file: 'olf_tool_small.png')}"/></g:else>
                    </g:editInPlace>
                </g:else>
            </span>

        </li>



        <li class="fieldcontain">
            <span id="object-label" class="property-label"><g:message code="project.object.label"
                                                                      default="Object"/></span>

            <span class="property-value" aria-labelledby="object-label">
                <ul>
                    <g:if test="${projectInstance.object.size() > 0}">
                        <g:each in="${projectInstance.object}" var="o">
                            <g:form name="remove${o}Form">
                                <li>
                                    <g:remoteLink controller="dataObject" action="show" id="${o.id}"
                                                  params="['bodyOnly': 'true']"
                                                  update="[success: 'body', failure: 'body']">${o?.encodeAsHTML()}</g:remoteLink>

                                    <g:hiddenField name="bodyOnly" value="${true}"/>
                                    <g:hiddenField name="id" value="${projectInstance?.id}"/>
                                    <g:hiddenField name="associatedId" value="${o?.id}"/>
                                    <g:hiddenField name="propertyName" value="dataObject"/>
                                    <g:hiddenField name="referencedClassName" value="org.openlab.main.DataObject"/>
                                    <g:hiddenField name="thisClassName" value="Project"/>
                                    <g:submitToRemote action="removeManyToMany"
                                                      update="[success: 'body', failure: 'body']" value="Remove"/>
                                </li>
                            </g:form>
                        </g:each>
                    </g:if>
                    <g:else>
                        <i>None added</i>
                    </g:else>
                </ul><br/><br/>
                <g:form name="addToProject">
                    <g:hiddenField name="bodyOnly" value="${true}"/>
                    <g:hiddenField name="referencedClassName" value="org.openlab.main.DataObject"/>
                    <g:select from="${org.openlab.main.DataObject.list(sort: 'name')}" name="selectAddTo"
                              optionKey="id"/>
                    <g:hiddenField name="id" value="${projectInstance?.id}"/>
                    <g:submitToRemote action="addManyToMany" update="[success: 'body', failure: 'body']"
                                      value="Add to"/>
                </g:form>
            </span>

        </li>



        <li class="fieldcontain">
            <span id="laboratory-label" class="property-label"><g:message code="project.laboratory.label"
                                                                          default="Laboratory"/></span>

            <span class="property-value" aria-labelledby="laboratory-label">
                <g:form>
                    <g:hiddenField name="bodyOnly" value="true"/>
                    <g:editCollectionInPlace id="laboratory"
                                             url="[action: 'editCollectionField', id: projectInstance.id]"
                                             paramName="laboratory"
                                             className="${Project}"
                                             referencedClassName="org.openlab.main.Laboratory">
                        <g:hiddenField name="id" value="${projectInstance?.laboratory?.id}"/>
                        <g:if test="${projectInstance?.laboratory}">${projectInstance?.laboratory?.encodeAsHTML()}</g:if>
                        <g:else><img src="${resource(dir: 'images/skin', file: 'olf_tool_small.png')}"/></g:else>
                    </g:editCollectionInPlace>
                    <g:if test="${fieldValue(bean: projectInstance, field: 'laboratory')}">
                        <g:submitToRemote controller="laboratory" action="show"
                                          update="[success: 'body', failure: 'error']" value="Show"/>
                    </g:if>
                </g:form>
            </span>

        </li>

    </ol>
</div>

<div id="tabs"/>
<g:renderInterestedModules id='${projectInstance?.id}' domainClass='project'/>

<script type="text/javascript">
    olfEvHandler.bodyContentChangedEvent.fire("${projectInstance?.toString()}", "${Project}", "${projectInstance?.id}");
</script>
</body>
</html>
