
<%@ page import="org.openlab.taqman.TaqManResult" %>
<!doctype html>
<html>
<head>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <g:set var="entityName" value="${message(code: 'taqManResult.label', default: 'TaqManResult')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" class="list" action="list" update="body"><g:message code="default.list.label" args="[entityName]" /></g:remoteLink></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:remoteLink></li>
        <li><g:remoteLink params="${[bodyOnly: true]}" class="delete" action="delete" id="${taqManResultInstance?.id}" update="body" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
            <g:message code='default.button.delete.label' default='Delete'/></g:remoteLink></li>
    </ul>
</div>
<g:render template="additionalBoxes" id="${taqManResultInstance?.id}"/>
<div id="show-taqManResult" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /> ${taqManResultInstance}</h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list taqManResult">



<li class="fieldcontain">
    <span id="attachment-label" class="property-label"><g:message code="taqManResult.attachment.label" default="Attachment" /></span>

    <span class="property-value" aria-labelledby="attachment-label">
        <div id="attachmentEditable">
            <div onclick="${g.remoteFunction(action:'updateEditable',                                                       id: taqManResultInstance?.id,
                    params:[thisClassName: 'TaqManResult',
                            referencedClassName: 'openlab.attachments.DataObjectAttachment',
                            propertyName: 'attachment'],
                    update:'attachmentEditable')}"
            >
                <g:if test="${taqManResultInstance?.attachment}">${taqManResultInstance?.attachment?.encodeAsHTML()}</g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>

            </div>
        </div>
    </span>

</li>



<li class="fieldcontain">
    <span id="cDNAtemplate-label" class="property-label"><g:message code="taqManResult.cDNAtemplate.label" default="CDNA template" /></span>

    <span class="property-value" aria-labelledby="cDNAtemplate-label">
        <g:set var="myInList" value="${taqManResultInstance?.constraints.cDNAtemplate.inList}"/>
        <g:if test="${myInList}">
            <g:select from="${myInList}" name="cDNAtemplateSelect" value="${fieldValue(bean: taqManResultInstance, field: 'cDNAtemplate')}"
                      onChange="${g.remoteFunction(action:'editInList',id: taqManResultInstance?.id, params: '"cDNAtemplate=" + this.value', onFailure:'alert("Could not save property change");')}"/>
        </g:if>
        <g:else>
            <g:editInPlace id="cDNAtemplate"
                           url="[action: 'editField', id:taqManResultInstance.id]"
                           rows="1"
                           paramName="cDNAtemplate">
                <g:if test="${fieldValue(bean: taqManResultInstance, field: 'cDNAtemplate')}">
                    ${fieldValue(bean: taqManResultInstance, field: 'cDNAtemplate')}
                </g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
            </g:editInPlace>
        </g:else>
    </span>

</li>



<li class="fieldcontain">
    <span id="purificationMethod-label" class="property-label"><g:message code="taqManResult.purificationMethod.label" default="Purification Method" /></span>

    <span class="property-value" aria-labelledby="purificationMethod-label">
        <div id="purificationMethodEditable">
            <div onclick="${g.remoteFunction(action:'updateEditable',                                                       id: taqManResultInstance?.id,
                    params:[thisClassName: 'TaqManResult',
                            referencedClassName: 'org.openlab.taqman.PurificationMethod',
                            propertyName: 'purificationMethod'],
                    update:'purificationMethodEditable')}"
            >
                <g:if test="${taqManResultInstance?.purificationMethod}">${taqManResultInstance?.purificationMethod?.encodeAsHTML()}</g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>

            </div>
        </div>
    </span>

</li>



<li class="fieldcontain">
    <span id="reverseTranscriptionPrimer-label" class="property-label"><g:message code="taqManResult.reverseTranscriptionPrimer.label" default="Reverse Transcription Primer" /></span>

    <span class="property-value" aria-labelledby="reverseTranscriptionPrimer-label">
        <div id="reverseTranscriptionPrimerEditable">
            <div onclick="${g.remoteFunction(action:'updateEditable',                                                       id: taqManResultInstance?.id,
                    params:[thisClassName: 'TaqManResult',
                            referencedClassName: 'org.openlab.taqman.ReverseTranscriptionPrimer',
                            propertyName: 'reverseTranscriptionPrimer'],
                    update:'reverseTranscriptionPrimerEditable')}"
            >
                <g:if test="${taqManResultInstance?.reverseTranscriptionPrimer}">${taqManResultInstance?.reverseTranscriptionPrimer?.encodeAsHTML()}</g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>

            </div>
        </div>
    </span>

</li>



<li class="fieldcontain">
    <span id="reverseTranscriptionKit-label" class="property-label"><g:message code="taqManResult.reverseTranscriptionKit.label" default="Reverse Transcription Kit" /></span>

    <span class="property-value" aria-labelledby="reverseTranscriptionKit-label">
        <div id="reverseTranscriptionKitEditable">
            <div onclick="${g.remoteFunction(action:'updateEditable',                                                       id: taqManResultInstance?.id,
                    params:[thisClassName: 'TaqManResult',
                            referencedClassName: 'org.openlab.taqman.ReverseTranscriptionKit',
                            propertyName: 'reverseTranscriptionKit'],
                    update:'reverseTranscriptionKitEditable')}"
            >
                <g:if test="${taqManResultInstance?.reverseTranscriptionKit}">${taqManResultInstance?.reverseTranscriptionKit?.encodeAsHTML()}</g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>

            </div>
        </div>
    </span>

</li>



<li class="fieldcontain">
    <span id="annealingTemperature-label" class="property-label"><g:message code="taqManResult.annealingTemperature.label" default="Annealing Temperature" /></span>

    <span class="property-value" aria-labelledby="annealingTemperature-label">
        <g:set var="myInList" value="${taqManResultInstance?.constraints.annealingTemperature.inList}"/>
        <g:if test="${myInList}">
            <g:select from="${myInList}" name="annealingTemperatureSelect" value="${fieldValue(bean: taqManResultInstance, field: 'annealingTemperature')}"
                      onChange="${g.remoteFunction(action:'editInList',id: taqManResultInstance?.id, params: '"annealingTemperature=" + this.value', onFailure:'alert("Could not save property change");')}"/>
        </g:if>
        <g:else>
            <g:editInPlace id="annealingTemperature"
                           url="[action: 'editField', id:taqManResultInstance.id]"
                           rows="1"
                           paramName="annealingTemperature">
                <g:if test="${fieldValue(bean: taqManResultInstance, field: 'annealingTemperature')}">
                    ${fieldValue(bean: taqManResultInstance, field: 'annealingTemperature')}
                </g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
            </g:editInPlace>
        </g:else>
    </span>

</li>



<li class="fieldcontain">
    <span id="cycleNumber-label" class="property-label"><g:message code="taqManResult.cycleNumber.label" default="Cycle Number" /></span>

    <span class="property-value" aria-labelledby="cycleNumber-label">
        <g:set var="myInList" value="${taqManResultInstance?.constraints.cycleNumber.inList}"/>
        <g:if test="${myInList}">
            <g:select from="${myInList}" name="cycleNumberSelect" value="${fieldValue(bean: taqManResultInstance, field: 'cycleNumber')}"
                      onChange="${g.remoteFunction(action:'editInList',id: taqManResultInstance?.id, params: '"cycleNumber=" + this.value', onFailure:'alert("Could not save property change");')}"/>
        </g:if>
        <g:else>
            <g:editInPlace id="cycleNumber"
                           url="[action: 'editField', id:taqManResultInstance.id]"
                           rows="1"
                           paramName="cycleNumber">
                <g:if test="${fieldValue(bean: taqManResultInstance, field: 'cycleNumber')}">
                    ${fieldValue(bean: taqManResultInstance, field: 'cycleNumber')}
                </g:if>
                <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>
            </g:editInPlace>
        </g:else>
    </span>

</li>



<li class="fieldcontain">
    <span id="detectorsAssigned-label" class="property-label"><g:message code="taqManResult.detectorsAssigned.label" default="Detectors Assigned" /></span>

    <span class="property-value" aria-labelledby="detectorsAssigned-label"><g:checkBox name="detectorsAssignedCheckbox" value="${taqManResultInstance?.detectorsAssigned}"
                                                                                       onChange="${g.remoteFunction(action:'editBoolean',id: taqManResultInstance?.id, params: [property: 'detectorsAssigned'], onFailure:'alert("Could not save property change");')}"/></span>

</li>



<li class="fieldcontain">
    <span id="samplesAssigned-label" class="property-label"><g:message code="taqManResult.samplesAssigned.label" default="Samples Assigned" /></span>

    <span class="property-value" aria-labelledby="samplesAssigned-label"><g:checkBox name="samplesAssignedCheckbox" value="${taqManResultInstance?.samplesAssigned}"
                                                                                     onChange="${g.remoteFunction(action:'editBoolean',id: taqManResultInstance?.id, params: [property: 'samplesAssigned'], onFailure:'alert("Could not save property change");')}"/></span>

</li>



<li class="fieldcontain">
    <span id="referenceResult-label" class="property-label"><g:message code="taqManResult.referenceResult.label" default="Reference Result" /></span>

    <span class="property-value" aria-labelledby="referenceResult-label"><g:checkBox name="referenceResultCheckbox" value="${taqManResultInstance?.referenceResult}"
                                                                                     onChange="${g.remoteFunction(action:'editBoolean',id: taqManResultInstance?.id, params: [property: 'referenceResult'], onFailure:'alert("Could not save property change");')}"/></span>

</li>



<li class="fieldcontain">
    <span id="dataObjects-label" class="property-label"><g:message code="taqManResult.dataObjects.label" default="Data Objects" /></span>

    <span class="property-value" aria-labelledby="dataObjects-label">
        <ul>
            <g:if test="${taqManResultInstance.dataObjects}">
                <g:each in="${taqManResultInstance.dataObjects}" var="d">
                    <g:form name="remove${d}Form">
                        <li>
                            <g:remoteLink controller="dataObject" action="show" id="${d.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">${d?.encodeAsHTML()}</g:remoteLink>

                            <g:hiddenField name="bodyOnly" value="${true}"/>
                            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
                            <g:hiddenField name="associatedId" value="${d?.id}"/>
                            <g:hiddenField name="propertyName" value="dataObjects"/>
                            <g:hiddenField name="referencedClassName" value="org.openlab.main.DataObject"/>
                            <g:hiddenField name="thisClassName" value="TaqManResult"/>
                            <g:submitToRemote action="removeOneToMany" update="[success:'body',failure:'body']" value="Remove" />
                        </li>
                    </g:form>
                </g:each>
            </g:if>
            <g:else>
                <i>None added</i>
            </g:else>
        </ul><br/><br/>
        <g:form name="addOneToMany">
            <g:hiddenField name="bodyOnly" value="${true}"/>
            <g:hiddenField name="propertyName" value="dataObjects"  />
            <g:hiddenField name="referencedClassName" value="org.openlab.main.DataObject"/>
            <g:select from="${org.openlab.main.DataObject.list()}" name="selectAddTo" optionKey="id"/>
            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
            <g:submitToRemote action="addOneToMany" update="[success:'body',failure:'body']" value="Add" />
        </g:form>
    </span>

</li>



<li class="fieldcontain">
    <span id="detectors-label" class="property-label"><g:message code="taqManResult.detectors.label" default="Detectors" /></span>

    <span class="property-value" aria-labelledby="detectors-label">
        <ul>
            <g:if test="${taqManResultInstance.detectors}">
                <g:each in="${taqManResultInstance.detectors}" var="d">
                    <g:form name="remove${d}Form">
                        <li>
                            <g:remoteLink controller="taqManAssay" action="show" id="${d.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">${d?.encodeAsHTML()}</g:remoteLink>

                            <g:hiddenField name="bodyOnly" value="${true}"/>
                            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
                            <g:hiddenField name="associatedId" value="${d?.id}"/>
                            <g:hiddenField name="propertyName" value="detectors"/>
                            <g:hiddenField name="referencedClassName" value="org.openlab.taqman.TaqManAssay"/>
                            <g:hiddenField name="thisClassName" value="TaqManResult"/>
                            <g:submitToRemote action="removeOneToMany" update="[success:'body',failure:'body']" value="Remove" />
                        </li>
                    </g:form>
                </g:each>
            </g:if>
            <g:else>
                <i>None added</i>
            </g:else>
        </ul><br/><br/>
        <g:form name="addOneToMany">
            <g:hiddenField name="bodyOnly" value="${true}"/>
            <g:hiddenField name="propertyName" value="detectors"  />
            <g:hiddenField name="referencedClassName" value="org.openlab.taqman.TaqManAssay"/>
            <g:select from="${org.openlab.taqman.TaqManAssay.list()}" name="selectAddTo" optionKey="id"/>
            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
            <g:submitToRemote action="addOneToMany" update="[success:'body',failure:'body']" value="Add" />
        </g:form>
    </span>

</li>



<li class="fieldcontain">
    <span id="samples-label" class="property-label"><g:message code="taqManResult.samples.label" default="Samples" /></span>

    <span class="property-value" aria-labelledby="samples-label">
        <ul>
            <g:if test="${taqManResultInstance.samples}">
                <g:each in="${taqManResultInstance.samples}" var="s">
                    <g:form name="remove${s}Form">
                        <li>
                            <g:remoteLink controller="taqManSample" action="show" id="${s.id}" params="['bodyOnly':'true']" update="[success: 'body', failure: 'body']">${s?.encodeAsHTML()}</g:remoteLink>

                            <g:hiddenField name="bodyOnly" value="${true}"/>
                            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
                            <g:hiddenField name="associatedId" value="${s?.id}"/>
                            <g:hiddenField name="propertyName" value="samples"/>
                            <g:hiddenField name="referencedClassName" value="org.openlab.taqman.TaqManSample"/>
                            <g:hiddenField name="thisClassName" value="TaqManResult"/>
                            <g:submitToRemote action="removeOneToMany" update="[success:'body',failure:'body']" value="Remove" />
                        </li>
                    </g:form>
                </g:each>
            </g:if>
            <g:else>
                <i>None added</i>
            </g:else>
        </ul><br/><br/>
        <g:form name="addOneToMany">
            <g:hiddenField name="bodyOnly" value="${true}"/>
            <g:hiddenField name="propertyName" value="samples"  />
            <g:hiddenField name="referencedClassName" value="org.openlab.taqman.TaqManSample"/>
            <g:select from="${org.openlab.taqman.TaqManSample.list()}" name="selectAddTo" optionKey="id"/>
            <g:hiddenField name="id" value="${taqManResultInstance?.id}"/>
            <g:submitToRemote action="addOneToMany" update="[success:'body',failure:'body']" value="Add" />
        </g:form>
    </span>

</li>


</ol>
</div>

<div id="tabs"/>
<g:renderInterestedModules id='${taqManResultInstance?.id}' domainClass='taqManResult'/>

<script type="text/javascript">
    olfEvHandler.bodyContentChangedEvent.fire("${taqManResultInstance?.toString()}", "${TaqManResult}" ,"${taqManResultInstance?.id}");
</script>
</body>
</html>


