<div id="barcodeMessage" style="padding-bottom:10px;">
    <h2 style="font-weight: bold;">Print Barcode Label</h2>
</div>
<g:form method="post" >
	<g:hiddenField name="dataObject.id" value="${dataObject?.id}"/>
	<g:hiddenField name="subDataObject.id" value="${subDataObject?.id}"/>
	<g:hiddenField name="barcodeDataObject.id" value="${barcodeDataObject?.id}"/>
    <g:if test="${update==true}">
	    <g:hiddenField name="id" value="${barcodeInstance?.id}" />
        <g:hiddenField name="version" value="${barcodeInstance?.version}" />
    </g:if>
    <div class="dialog">
        <table>
            <tbody>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="description"><g:message code="barcode.site.label" default="Site" /></label><g:select name="site.id" from="${org.openlab.barcode.BarcodeSite.list()}" optionKey="id" value="${barcodeInstance?.site?.id}"  />
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: barcodeInstance, field: 'site', 'errors')}">
                        <label for="label"><g:message code="barcode.label.label" default="Label" /></label><g:select name="label.id" from="${org.openlab.barcode.BarcodeLabel.list()}" optionKey="id" value="${barcodeInstance?.label?.id}"  />
                    </td>
            </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="description"><g:message code="barcode.description.label" default="Description" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: barcodeInstance, field: 'description', 'errors')}">
                        <g:textField onmouseover="this.select();" name="description" value="${barcodeInstance?.description}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="text"><g:message code="barcode.text.label" default="Text" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: barcodeInstance, field: 'text', 'errors')}">
                        <g:if test="${dataObject instanceof org.openlab.genetracker.CellLineData}">
                        	<g:select name="text" from="${passages}" optionKey="passageNr" />
                        </g:if>
                        <g:else>
                        	<g:textField onmouseover="this.select();" name="text" value="${barcodeInstance?.text}" />
                        </g:else>
                    </td>
                </tr>            
            </tbody>
        </table>
    </div>
    <div class="buttons">
        <span class="button"><g:remoteLink controller="barcode" params="${[barcodeDataObject: barcodeDataObject, 'barcodeDataObject.id': barcodeDataObject.id, dataObject: dataObject, 'dataObject.id': dataObject.id]}" action="scannerAddin" update="barcodeCreator">Scan</g:remoteLink></span>
         <g:if test="${dataObject instanceof org.openlab.genetracker.CellLineData}">
        	<span class="button">
        		<g:remoteLink controller="barcode" params="${[barcodeDataObject: barcodeDataObject, 'barcodeDataObject.id': barcodeDataObject.id, dataObject: dataObject, 'dataObject.id': dataObject.id]}" update="barcodeCreator" action="renderBarcodeCreator">
	Passage</g:remoteLink>
			</span>
		</g:if>
        
        <g:if test="${update==false}">
        	<span class="button"><g:submitToRemote action="saveAndPrint" update="barcodeCreator" name="create" class="save" value="Preview" /></span>
        </g:if>
        <g:else>
        	<span class="button"><g:submitToRemote action="updateAndPrint" update="barcodeCreator" name="create" class="save" value="Preview" /></span>
        </g:else>
    </div>
</g:form>