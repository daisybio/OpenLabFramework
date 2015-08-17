<% import grails.persistence.Event %>
<%=packageName%>
<%	excludedProps = Event.allEvents.toList() << 'version' << 'attachable'  << 'id' << 'acl' << 'accessLevel' %>
<% historyProps = [] << 'creator' << 'dateCreated' << 'lastModifier' << 'lastUpdated' %>

<div id="createAdditionalContent"></div>