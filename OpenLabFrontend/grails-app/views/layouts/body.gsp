<head>
    <!-- Main CSS at the end to make sure it is effective -->
    <r:require module="prototypeManual"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'select2.css')}"/>

    <r:layoutResources/>
    <g:set var="historyURL" value="${request.forwardURI+params.toQueryString()}" />

<!-- update history -->
    <g:if test="${!params.moveInHistory}">
        <script type="text/javascript">
            document.getElementById("navigationFlagInput").value = "true";
            var currentState = YAHOO.util.History.getCurrentState("bodyContent");

            if("${historyURL}" != currentState.toString())
            {
                YAHOO.util.History.navigate("bodyContent", "${historyURL}");
            }

        </script>
    </g:if>

</head>
<body>
    <g:layoutBody/>

    <!-- init select boxes -->
    <script type="text/javascript">
        YAHOO.util.Event.onDOMReady(function () { jQuery(".select2").select2({width:165}); });
    </script>

    <r:layoutResources/>
</body>