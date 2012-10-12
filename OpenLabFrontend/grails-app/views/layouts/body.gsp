<head>
    <!-- Main CSS at the end to make sure it is effective -->
    <r:require module="prototypeManual"/>
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
    <r:layoutResources/>
</body>