<%@ page import="openlab.attachments.DataObjectAttachment" %>




<div class="fieldcontain ${hasErrors(bean: dataObjectAttachmentInstance, field: 'dataObjects', 'error')} ">
	<label for="dataObjects">
		<g:message code="dataObjectAttachment.dataObjects.label" default="Data Objects" />
		
	</label>
	<g:select name="dataObjects" from="${org.openlab.main.DataObject.list()}" multiple="multiple" optionKey="id" size="5" value="${dataObjectAttachmentInstance?.dataObjects*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataObjectAttachmentInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="dataObjectAttachment.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${dataObjectAttachmentInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataObjectAttachmentInstance, field: 'fileName', 'error')} ">
	<label for="fileName">
		<g:message code="dataObjectAttachment.fileName.label" default="File Name" />
		
	</label>
	<g:textField name="fileName" value="${dataObjectAttachmentInstance?.fileName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataObjectAttachmentInstance, field: 'fileType', 'error')} ">
	<label for="fileType">
		<g:message code="dataObjectAttachment.fileType.label" default="File Type" />
		
	</label>
	<g:textField name="fileType" value="${dataObjectAttachmentInstance?.fileType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataObjectAttachmentInstance, field: 'fileUploadDate', 'error')} required">
	<label for="fileUploadDate">
		<g:message code="dataObjectAttachment.fileUploadDate.label" default="File Upload Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fileUploadDate" precision="day"  value="${dataObjectAttachmentInstance?.fileUploadDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: dataObjectAttachmentInstance, field: 'pathToFile', 'error')} ">
	<label for="pathToFile">
		<g:message code="dataObjectAttachment.pathToFile.label" default="Path To File" />
		
	</label>
	<g:textField name="pathToFile" value="${dataObjectAttachmentInstance?.pathToFile}"/>
</div>

