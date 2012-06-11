<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    
    <title>Language</title>
</head>

<body>
<div class="body" style="width:97%;">
	<h1>Language</h1>
     <g:if test="${flash.message}">
       <div class="message">${flash.message}</div>
     </g:if>
     
     <div id="msg"></div>

	<g:select name="languageSelect" from="${languages}" value="${userLanguage}" onChange="${remoteFunction(params: '\'lang=\'+this.value', controller:'usersettings', action: 'changeLanguage', update:'msg')}"></g:select>
</div>
</body>
</html>