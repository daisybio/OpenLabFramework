<%@ page import="org.openlab.main.Project" %>


<div style="padding-right:20px; padding-top: 20px; position:absolute; right:0;">

    <gui:expandablePanel title="History" expanded="true" closable="true">
        <table style="border: 0;"><tbody>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="project.creator.label" default="Creator"/></td>

            <td valign="top" class="value">${projectInstance?.creator?.encodeAsHTML()}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="project.dateCreated.label" default="Date Created"/></td>

            <td valign="top" class="value"><g:formatDate date="${projectInstance?.dateCreated}"/></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="project.lastModifier.label" default="Last Modifier"/></td>

            <td valign="top" class="value">${projectInstance?.lastModifier?.encodeAsHTML()}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="project.lastUpdate.label" default="Last Update"/></td>

            <td valign="top" class="value"><g:formatDate date="${projectInstance?.lastUpdate}"/></td>

        </tr>

        </tbody>
        </table>
    </gui:expandablePanel>


    <div style="padding-top:10px;">
        <gui:expandablePanel title="Operations" expanded="true" closable="false">
            <ul>
                <g:includeOperationsForType domainClass="project" id="${projectInstance.id}"/>
            </ul>
        </gui:expandablePanel>
    </div>
</div>