<html>
<head>
    <title>Administration System</title>
    <link rel="stylesheet"
            href="${createLinkTo(dir: 'css', file: 'main.css')}"/>
    <g:javascript library="application"/>
    <g:javascript library="prototype"/>
    <script type="text/javascript">
        function refresh()
        {
            window.location.reload(false);
        }

        function loading() {
            document.getElementById('spinner').style.display = 'inline';
            document.getElementById('error').style.display = 'none';
        }
      
        function showError(e) {
            var errorDiv = document.getElementById('error')
            errorDiv.innerHTML = '<ul><li>'
                     + e.responseText + '</li></ul>';
            errorDiv.style.display = 'block';
        }
    </script>
</head>
<body>
<div class="logo">
    <div style="margin-left:10px;">
        <h1>Current Environment: 
               ${session.environment?.name ?: 'None'}</h1>
        <form action="">
            <g:select name="environment" 
                   from="${org.openlab.datasource.Environment.list()}" 
                      optionKey="id" optionValue="name" 
                       value="${session.environment?.id}"/>
            <g:passwordField name="password"/>
            <g:submitToRemote value="Select" controller="environment" 
             action="change" update="currentEnv"
             onLoading="loading();"
             onComplete
               ="document.getElementById('spinner').style.display='none';"
             onFailure="showError(e)"
             onSuccess="refresh()"/> <br/>
            <div class="errors" id="error" 
              style="display:none;width:500px;">

            </div>
            <img id="spinner" style="display:none;"
              src="${createLinkTo(dir: 'images', file: 'spinner.gif')}"
              alt="Spinner"/>
        </form>
    </div>
</div>
</body>
</html>
