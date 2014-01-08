<%@ page import="org.openlab.main.Project" %>




<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="project.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${projectInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="project.description.label" default="Description"/>

    </label>
    <g:textField name="description" value="${projectInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'laboratory', 'error')} required">
    <label for="laboratory">
        <g:message code="project.laboratory.label" default="Laboratory"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="laboratory" name="laboratory.id" from="${org.openlab.main.Laboratory.list()}" optionKey="id"
              required="" value="${projectInstance?.laboratory?.id}" class="many-to-one"/>
</div>

