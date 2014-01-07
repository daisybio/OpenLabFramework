<%@ page import="org.openlab.barcode.BarcodeLabel" %>




<div class="fieldcontain ${hasErrors(bean: barcodeLabelInstance, field: 'barcodeType', 'error')} ">
	<label for="barcodeType">
		<g:message code="barcodeLabel.barcodeType.label" default="Barcode Type" />
		
	</label>
	<g:textField name="barcodeType" value="${barcodeLabelInstance?.barcodeType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: barcodeLabelInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="barcodeLabel.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${barcodeLabelInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: barcodeLabelInstance, field: 'xml', 'error')} ">
	<label for="xml">
		<g:message code="barcodeLabel.xml.label" default="Xml" />
		
	</label>
	<g:textArea name="xml" value="${barcodeLabelInstance?.xml}" rows="100" cols="60"/>
</div>

