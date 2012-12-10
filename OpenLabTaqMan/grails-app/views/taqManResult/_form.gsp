<%@ page import="org.openlab.taqman.TaqManResult" %>



<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'creator', 'error')} ">
    <label for="creator">
        <g:message code="taqManResult.creator.label" default="Creator"/>

    </label>
    <g:select id="creator" name="creator.id" from="${org.openlab.security.User.list()}" optionKey="id"
              value="${taqManResultInstance?.creator?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'lastModifier', 'error')} ">
    <label for="lastModifier">
        <g:message code="taqManResult.lastModifier.label" default="Last Modifier"/>

    </label>
    <g:select id="lastModifier" name="lastModifier.id" from="${org.openlab.security.User.list()}" optionKey="id"
              value="${taqManResultInstance?.lastModifier?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'lastUpdate', 'error')} ">
    <label for="lastUpdate">
        <g:message code="taqManResult.lastUpdate.label" default="Last Update"/>

    </label>
    <g:datePicker name="lastUpdate" precision="day" value="${taqManResultInstance?.lastUpdate}" default="none"
                  noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'attachment', 'error')} required">
    <label for="attachment">
        <g:message code="taqManResult.attachment.label" default="Attachment"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="attachment" name="attachment.id" from="${openlab.attachments.DataObjectAttachment.list()}"
              optionKey="id" required="" value="${taqManResultInstance?.attachment?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'cDNAtemplate', 'error')} ">
    <label for="cDNAtemplate">
        <g:message code="taqManResult.cDNAtemplate.label" default="CDNA template"/>

    </label>
    <g:select name="cDNAtemplate" from="${taqManResultInstance.constraints.cDNAtemplate.inList}"
              value="${taqManResultInstance?.cDNAtemplate}" valueMessagePrefix="taqManResult.cDNAtemplate"
              noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'purificationMethod', 'error')} required">
    <label for="purificationMethod">
        <g:message code="taqManResult.purificationMethod.label" default="Purification Method"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="purificationMethod" name="purificationMethod.id"
              from="${org.openlab.taqman.PurificationMethod.list()}" optionKey="id" required=""
              value="${taqManResultInstance?.purificationMethod?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'reverseTranscriptionPrimer', 'error')} required">
    <label for="reverseTranscriptionPrimer">
        <g:message code="taqManResult.reverseTranscriptionPrimer.label" default="Reverse Transcription Primer"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="reverseTranscriptionPrimer" name="reverseTranscriptionPrimer.id"
              from="${org.openlab.taqman.ReverseTranscriptionPrimer.list()}" optionKey="id" required=""
              value="${taqManResultInstance?.reverseTranscriptionPrimer?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'reverseTranscriptionKit', 'error')} required">
    <label for="reverseTranscriptionKit">
        <g:message code="taqManResult.reverseTranscriptionKit.label" default="Reverse Transcription Kit"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="reverseTranscriptionKit" name="reverseTranscriptionKit.id"
              from="${org.openlab.taqman.ReverseTranscriptionKit.list()}" optionKey="id" required=""
              value="${taqManResultInstance?.reverseTranscriptionKit?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'annealingTemperature', 'error')} required">
    <label for="annealingTemperature">
        <g:message code="taqManResult.annealingTemperature.label" default="Annealing Temperature"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="annealingTemperature" type="number" value="${taqManResultInstance.annealingTemperature}"
             required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'cycleNumber', 'error')} required">
    <label for="cycleNumber">
        <g:message code="taqManResult.cycleNumber.label" default="Cycle Number"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="cycleNumber" type="number" value="${taqManResultInstance.cycleNumber}" required=""/>
</div>

<g:hiddenField name="detectorsAssigned" value="${taqManResultInstance?.detectorsAssigned?:false}"/>

<g:hiddenField name="samplesAssigned" value="${taqManResultInstance?.samplesAssigned?:false}"/>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'referenceResult', 'error')} ">
    <label for="referenceResult">
        <g:message code="taqManResult.referenceResult.label" default="Reference Result"/>

    </label>
    <g:checkBox name="referenceResult" value="${taqManResultInstance?.referenceResult}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'dataObjects', 'error')} ">
    <label for="dataObjects">
        <g:message code="taqManResult.dataObjects.label" default="Data Objects"/>

    </label>
    <g:select name="dataObjects" from="${org.openlab.main.DataObject.list()}" multiple="multiple" optionKey="id"
              size="5" value="${taqManResultInstance?.dataObjects*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'detectors', 'error')} ">
    <label for="detectors">
        <g:message code="taqManResult.detectors.label" default="Detectors"/>

    </label>
    <g:select name="detectors" from="${org.openlab.taqman.TaqManAssay.list()}" multiple="multiple" optionKey="id"
              size="5" value="${taqManResultInstance?.detectors*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: taqManResultInstance, field: 'samples', 'error')} ">
    <label for="samples">
        <g:message code="taqManResult.samples.label" default="Samples"/>

    </label>

    <ul class="one-to-many">
        <g:each in="${taqManResultInstance?.samples ?}" var="s">
            <li><g:link controller="taqManSample" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
        </g:each>
        <!--<li class="add">
            <g:link controller="taqManSample" action="create"
                    params="['taqManResult.id': taqManResultInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'taqManSample.label', default: 'TaqManSample')])}</g:link>
        </li>-->
    </ul>

</div>

