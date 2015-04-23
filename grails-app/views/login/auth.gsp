<html>
<head>
	<title><g:message code="springSecurity.login.title"/></title>
    <meta name="viewport" content="width=device-width"/>
    <g:if test="${!mobile}">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    </g:if>
    <g:else>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'login_mobile.css')}"/>
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css"/>
    </g:else>
</head>

<body>
<div id='login' style="padding:50px;">
    <g:if test="${!mobile}">
        <div>
            <img src="<g:resource dir="images" file="welcome_logo.jpg"/>"/>
        </div>
    </g:if>
    <g:else>
        <div>
            <img src="<g:resource dir="images" file="welcome_logo_mobile.jpg"/>"/>
        </div>
    </g:else>
	<div class='inner'>
		<div class='fheader'><g:message code="springSecurity.login.header"/></div>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<p>
				<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input type='text' class='text_' name='j_username' id='username'/>
			</p>

			<p>
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</p>

			<p id="remember_me_holder">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
			</p>

			<p>
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
			</p>
		</form>
	</div>

</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
