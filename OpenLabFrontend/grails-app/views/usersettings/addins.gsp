<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    
    <title>Addins</title>
</head>

<body>
<div class="body" style="width:97%;">
	<h1>Addins</h1>
     <g:if test="${flash.message}">
       <div class="message">${flash.message}</div>
     </g:if>
     <div class="message">You have to reload the page for changes to take effect on the Addin Panel.</div>
     <div id="msg"></div>
    
    Number of Addins: <g:select from="${1..10}" value="${numberOfAddins}" onChange="${remoteFunction(params: '\'numOfAddins=\'+this.value', update:'selectAddins', action:'numOfAddinsChanged', controller:'usersettings')}"></g:select> 
    
    <div id="selectAddins" style="width:300px;"><g:render template="/layouts/selectAddins"></g:render></div>
</div>
</body>
</html>