<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    
    <title>Addins</title>
</head>

<body>
<div class="body" style="width:97%;">
	<h1 style="padding-left:20px; padding-top: 10px;">Addins</h1>
     <g:if test="${flash.message}">
       <div class="message">${flash.message}</div>
     </g:if>
     <div class="message">You have to reload the page for changes to take effect on the Addin Panel.</div>

    <div id="msg"></div>
    
    <div style="padding:20px;">Number of Addins: <g:select name="numOfAddins" from="${1..10}" value="${numberOfAddins}"
                                onChange="${remoteFunction(params: '\'numOfAddins=\'+this.value', update:'selectAddins', action:'numOfAddinsChanged', controller:'usersettings')}"/>
    </div>
    <div id="selectAddins" style="padding-left:20px; width:300px;"><g:render template="/layouts/selectAddins"></g:render></div>
</div>
</body>
</html>