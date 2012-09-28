<%@ page import="org.openlab.storage.Box" %>
<!doctype html>
<html>
<div id="list-box" class="content scaffold-list" role="main">
    <h1>Boxes</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="description"
                              title="${message(code: 'box.description.label', default: 'Description')}"/>

            <g:sortableColumn property="lastUpdate"
                              title="${message(code: 'box.lastUpdate.label', default: 'Last Update')}"/>

            <g:sortableColumn property="xdim" title="${message(code: 'box.xdim.label', default: 'Xdim')}"/>

            <g:sortableColumn property="ydim" title="${message(code: 'box.ydim.label', default: 'Ydim')}"/>

            <th/>
            <th/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${boxInstanceList}" status="i" var="boxInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

            <td><g:editInPlace id="description_${boxInstance.id}"
                           url="[controller: 'box', action: 'editField', id: boxInstance.id]"
                           rows="1"
                           cols="10"
                           paramName="description">
                <g:if test="${fieldValue(bean: boxInstance, field: 'description') !=''}">
                    ${fieldValue(bean: boxInstance, field: "description")}
                </g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
            </g:editInPlace></td>

                <td><g:formatDate date="${boxInstance.lastUpdate}"/></td>

                <td>${fieldValue(bean: boxInstance, field: "xdim")}</td>

                <td>${fieldValue(bean: boxInstance, field: "ydim")}</td>

                <td><g:remoteLink update="boxArea" controller="box" action="showBox" id="${boxInstance.id}">Show</g:remoteLink></td>
                <td><g:remoteLink update="boxListArea" controller="box" action="deleteBox" id="${boxInstance.id}"
                                  before="if(!confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}')) return false;">Remove</g:remoteLink></td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${boxInstanceTotal}"/>
    </div>
</div>
</body>
</html>
