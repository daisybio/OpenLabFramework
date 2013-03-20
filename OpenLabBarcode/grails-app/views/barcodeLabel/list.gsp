
<%@ page import="org.openlab.barcode.BarcodeLabel" %>
<!doctype html>
<html>
	<head>
        <g:setProvider library="prototype"/>
        <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
		<g:set var="entityName" value="${message(code: 'barcodeLabel.label', default: 'BarcodeLabel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-barcodeLabel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:remoteLink params="${[bodyOnly: true]}" update="body" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:remoteLink></li>
			</ul>
		</div>
        <div id="list-barcodeLabel" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

            <div id="filter" class="boxShadow">
                <h2>Filter options:</h2>
                <div style="padding:15px;"/>
                <g:formRemote update="body" name="filterList" url="[controller: 'barcodeLabel', action:'list']">
                    <g:hiddenField name="bodyOnly" value="${true}"/>
                    Results per page: <g:select name="max" value="${params.max?:10}" from="${10..100}" class="range"/>
                    
                    
                    
                    <g:submitButton name="Filter"/>
                </g:formRemote>
                </div>
            </div>

			<table>
				<thead>
					<tr>
					

						<g:remoteSortableColumn property="barcodeType" params="${params}" title="${message(code: 'barcodeLabel.barcodeType.label', default: 'Barcode Type')}" />

						<g:remoteSortableColumn property="name" params="${params}" title="${message(code: 'barcodeLabel.name.label', default: 'Name')}" />
                        <th/>
					</tr>
				</thead>
				<tbody>
				<g:each in="${barcodeLabelInstanceList}" status="i" var="barcodeLabelInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: barcodeLabelInstance, field: "barcodeType")}</td>
					
                        <td><g:editInPlace id="name_${barcodeLabelInstance.id}"
                                       url="[action: 'editField', id: barcodeLabelInstance.id]"
                                       rows="1"
                                       cols= "10"
                                       paramName="name">${fieldValue(bean: barcodeLabelInstance, field: "name")}
                            </g:editInPlace>
                        </td>

                        <td><g:remoteLink params="${[bodyOnly: true]}" action="edit" id="${barcodeLabelInstance.id}" update="body">edit</g:remoteLink></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:remotePaginate total="${barcodeLabelInstanceTotal}?:0" params="${params}" />
			</div>
		</div>
    <script type="text/javascript">
        olfEvHandler.bodyContentChangedEvent.fire("${barcodeLabelInstance?.toString()}");
    </script>
	</body>
</html>
