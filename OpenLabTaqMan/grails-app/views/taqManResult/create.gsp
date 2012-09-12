<%@ page import="org.openlab.taqman.TaqManResult" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <g:set var="entityName" value="${message(code: 'taqManResult.label', default: 'TaqManResult')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${taqManResultInstance}">
        <div class="errors">
            <g:renderErrors bean="${taqManResultInstance}" as="list"/>
        </div>
    </g:hasErrors>

    <g:form action="save">
        <div class="dialog">

            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="referenceResult"><g:message code="taqManResult.referenceResult.label"
                                                                default="Reference Result"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'referenceResult', 'errors')}">
                        <g:checkBox name="referenceResult" value="${taqManResultInstance?.referenceResult}"/>
                    </td>
                    <td rowspan=10><div style="float: right; width: 200px;margin-right: 20px;"><g:render template="status" model="${[taqManResultInstance: taqManResultInstance]}"/></div></td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="cDNAtemplate"><g:message code="taqManResult.cDNAtemplate.label"
                                                             default="CDNA template"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'cDNAtemplate', 'errors')}">
                        <g:select name="cDNAtemplate" from="${taqManResultInstance.constraints.cDNAtemplate.inList}"
                                  value="${taqManResultInstance?.cDNAtemplate}"
                                  valueMessagePrefix="taqManResult.cDNAtemplate"/>
                    </td>
                </tr>



                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="annealingTemperature"><g:message code="taqManResult.annealingTemperature.label"
                                                                     default="Annealing Temperature"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'annealingTemperature', 'errors')}">
                        <g:textField name="annealingTemperature"
                                     value="${fieldValue(bean: taqManResultInstance, field: 'annealingTemperature')}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="attachment"><g:message code="taqManResult.attachment.label"
                                                           default="Attachment"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'attachment', 'errors')}">
                        <g:select name="attachment.id" from="${openlab.attachments.DataObjectAttachment.list()}"
                                  optionKey="id" value="${taqManResultInstance?.attachment?.id}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="cycleNumber"><g:message code="taqManResult.cycleNumber.label"
                                                            default="Cycle Number"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'cycleNumber', 'errors')}">
                        <g:textField name="cycleNumber"
                                     value="${fieldValue(bean: taqManResultInstance, field: 'cycleNumber')}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="purificationMethod"><g:message code="taqManResult.purificationMethod.label"
                                                                   default="Purification Method"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'purificationMethod', 'errors')}">
                        <g:select name="purificationMethod.id" from="${org.openlab.taqman.PurificationMethod.list()}"
                                  optionKey="id" value="${taqManResultInstance?.purificationMethod?.id}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="reverseTranscriptionKit"><g:message
                                code="taqManResult.reverseTranscriptionKit.label"
                                default="Reverse Transcription Kit"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'reverseTranscriptionKit', 'errors')}">
                        <g:select name="reverseTranscriptionKit.id"
                                  from="${org.openlab.taqman.ReverseTranscriptionKit.list()}" optionKey="id"
                                  value="${taqManResultInstance?.reverseTranscriptionKit?.id}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="reverseTranscriptionPrimer"><g:message
                                code="taqManResult.reverseTranscriptionPrimer.label"
                                default="Reverse Transcription Primer"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: taqManResultInstance, field: 'reverseTranscriptionPrimer', 'errors')}">
                        <g:select name="reverseTranscriptionPrimer.id"
                                  from="${org.openlab.taqman.ReverseTranscriptionPrimer.list()}" optionKey="id"
                                  value="${taqManResultInstance?.reverseTranscriptionPrimer?.id}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save"
                                                 value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
