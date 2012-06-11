<table>
    <tbody>


    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.referenceResult.label"
                                                 default="Reference Result"/></td>

        <td valign="top" class="value"><g:formatBoolean
                boolean="${taqManResultInstance?.referenceResult}"/></td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.cDNAtemplate.label"
                                                 default="CDNA template"/></td>

        <td valign="top" class="value">${fieldValue(bean: taqManResultInstance, field: "cDNAtemplate")}</td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.annealingTemperature.label"
                                                 default="Annealing Temperature"/></td>

        <td valign="top"
            class="value">${fieldValue(bean: taqManResultInstance, field: "annealingTemperature")}</td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.attachment.label"
                                                 default="Attachment"/></td>

        <td valign="top" class="value">
            <g:link controller="dataObjectAttachment" action="download" id="${taqManResultInstance?.attachment?.id}">${fieldValue(bean: taqManResultInstance?.attachment, field: "fileName")}</g:link></td>
    </td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.cycleNumber.label"
                                                 default="Cycle Number"/></td>

        <td valign="top" class="value">${fieldValue(bean: taqManResultInstance, field: "cycleNumber")}</td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.dataObjects.label"
                                                 default="Data Objects"/></td>

        <td valign="top" style="text-align: left;" class="value">
            <ul>
                <g:each in="${taqManResultInstance.dataObjects}" var="d">
                    <li><g:link controller="dataObject" action="show"
                                id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
                </g:each>
            </ul>
        </td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.purificationMethod.label"
                                                 default="Purification Method"/></td>

        <td valign="top" class="value"><g:link controller="purificationMethod" action="show"
                                               id="${taqManResultInstance?.purificationMethod?.id}">${taqManResultInstance?.purificationMethod?.encodeAsHTML()}</g:link></td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.reverseTranscriptionKit.label"
                                                 default="Reverse Transcription Kit"/></td>

        <td valign="top" class="value"><g:link controller="reverseTranscriptionKit" action="show"
                                               id="${taqManResultInstance?.reverseTranscriptionKit?.id}">${taqManResultInstance?.reverseTranscriptionKit?.encodeAsHTML()}</g:link></td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.reverseTranscriptionPrimer.label"
                                                 default="Reverse Transcription Primer"/></td>

        <td valign="top" class="value"><g:link controller="reverseTranscriptionPrimer" action="show"
                                               id="${taqManResultInstance?.reverseTranscriptionPrimer?.id}">${taqManResultInstance?.reverseTranscriptionPrimer?.encodeAsHTML()}</g:link></td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.samples.label" default="Samples"/></td>

        <td valign="top" style="text-align: left;" class="value">
            <ul>
                <g:each in="${taqManResultInstance.samples}" var="s">
                    <li><g:link controller="taqManSample" action="show"
                                id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                </g:each>
            </ul>
        </td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="taqManResult.detectors.label" default="Detectors"/></td>

        <td valign="top" style="text-align: left;" class="value">
            <ul>
                <g:each in="${taqManResultInstance.detectors}" var="d">
                    <li><g:link controller="taqManAssay" action="show"
                                id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
                </g:each>
            </ul>
        </td>

    </tr>


    </tbody>
</table>