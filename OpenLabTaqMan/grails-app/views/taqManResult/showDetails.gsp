<%--
  Created by IntelliJ IDEA.
  User: mlist
  Date: 22-03-12
  Time: 14:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<gui:expandablePanel title="${taqManResultInstance}" expanded="true">

<g:render template="status" model="['taqManResultInstance': taqManResultInstance]"/>

<g:render template="showDetails" model="['taqManResultInstance': taqManResultInstance]"/>

</gui:expandablePanel>
</html>