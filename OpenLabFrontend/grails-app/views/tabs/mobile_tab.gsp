<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="body_mobile" />
    <title>Open Laboratory Framework</title>
    <g:setProvider library="prototype"/>
</head>

<body>
    <g:remoteLink data-role="button" controller="${lastController}" action="show" id="${lastId}" update="body">Back</g:remoteLink>

    <div id="tabs" data-type="scrollable" style="min-height:100px;">
        <g:render template="${templateName}" plugin="${templatePlugin}" model="${templateModel}"/>
    </div>

</body>
</html>