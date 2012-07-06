<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    
    <title>Language</title>
</head>

<body>
<div class="body" style="width:97%;">
	<h1 style="padding-left:20px; padding-top: 10px;">Language</h1>
     <g:if test="${flash.message}">
       <div class="message">${flash.message}</div>
     </g:if>
     
     <div class="message" id="msg">Select a language</div>
    <div style="padding:20px;">
        <br/>
        <g:select name="languageSelect" from="${languages}" value="${userLanguage}"
              onChange="${remoteFunction(params: '\'lang=\'+this.value', controller:'usersettings', action: 'changeLanguage', update:'msg')}">
         </g:select>
    </div>
</div>
</body>
</html>