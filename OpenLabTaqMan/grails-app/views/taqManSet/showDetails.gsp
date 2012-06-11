<%--
  Created by IntelliJ IDEA.
  User: mlist
  Date: 22-03-12
  Time: 12:16
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<body>

<gui:expandablePanel title="TaqManResults in selected set" expanded="true">
    <ul>
    <g:each in="${taqManSetResults}" var="${taqManResult}">
       <li> <g:remoteLink controller="taqManResult" action="showDetails" update="taqManResultDetails" id="${taqManResult.id}">${taqManResult}</g:remoteLink> </li>
    </g:each>
    </ul>
</gui:expandablePanel>

</body>
</html>