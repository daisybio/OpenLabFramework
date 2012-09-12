<%@ page import="org.openlab.taqman.TaqManResult; org.openlab.taqman.TaqManSample; org.openlab.genetracker.CellLineData" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    <title>Check detectors in TaqMan file</title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>

<div style="float: right; margin-right: 20px;"><g:render template="status" model="${[taqManResultInstance: taqManResultInstance]}"/></div>
<div style="margin-left: 10px;">

    <h1>Assign each sample in your TaqManResult to a sample in the database</h1>  <br><br>

    <b>If you find that a desired inducer is missing add it here</b> <br>
    Reselect "sample" in the combobox to get an updated selection.<br><br>

    Add new Inducer:<br>

    <div class="statusInducer" class="message"/>
    <g:formRemote name="addInducer" update="statusInducer" url="[controller: 'inducer', action: 'save']"><br>
        Name: <g:textField name="name"></g:textField><br/>
        Concentration (only numerical, no units): <g:textField name="concentration"></g:textField>  </br>
        <g:submitButton name="create"/>
    </g:formRemote> <br/>

    <b>Assign samples:</b><br/>

    <g:form name="selectMatchingSamples" action="saveSampleAssignment">

        <g:hiddenField name="taqManResultId" value="${taqManResultInstance.id}"/>
        <table style="width:800px;">
            <g:each in="${samples}" var="sample">

                <tr>
                    <td style="width:20%">${sample}:</td>
                    <td>
                        <g:set var="tqSample" value="${assignedSamples.find{it.sampleName.equals(sample)}}"></g:set>
                        <g:select name="type_${sample}" from="${sampleTypes}"  value="${tqSample?.sampleType}"
                                                     onChange="if(this.value == 'sample')
                      ${remoteFunction(controller: 'taqManResult', action: 'showSampleSelection', update: [success: 'sampleSelection_' + sample, failure: 'body'], params: [sampleName: sample])}
                     else document.getElementById('sampleSelection_${sample}').innerHTML = '';"/>
                </td>
                    <td><div style="text-align: left" id="sampleSelection_${sample}" onmouseout="this.style.background = 'white'" 
                             onmouseover="this.style.background = 'yellow';">
                        <g:if test="${tqSample?.sampleType == 'sample' }">
                        <div onclick="${remoteFunction(controller: 'taqManResult', action: 'showSampleSelection', update: [success: 'sampleSelection_' + sample, failure: 'body'], params: [sampleName: sample])}">
                            <g:hiddenField name="assignedSample_${sample.replace('+', '_')}" value="${tqSample?.cellLineData}"/>
                            <g:hiddenField name="assignedSample_${sample.replace('+', '_')}_id" value="${tqSample?.cellLineData?.id}"/>
                            <g:hiddenField name="inducer_${sample.replace('+', '_')}_id" value="${tqSample?.inducer?.id}"/>
                            ${tqSample?.cellLineData} <g:if test="${tqSample?.inducer}">induced by ${tqSample.inducer}</g:if>
                        </div>
                        </g:if>
                    </div>
                    </td>
                </tr>

            </g:each>
        </table>
        <g:submitButton name="submit"/>

    </g:form>

</div>

</body>
</html>