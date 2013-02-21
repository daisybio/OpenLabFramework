<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
 "http://www.w3.org/TR/html4/strict.dtd">
<html>

<!-- HEAD -->
<head>
    <!-- Title -->
    <title><g:layoutTitle default="OpenLaboratoryFramework"/></title>

    <!-- Core + Skin CSS -->
    <g:loadIcon/>


    <!-- JS resources -->
    <r:require modules="yui-core, yui-base, yui-event"/>
    <r:require module="jquery"/>
    <r:require module="jstree"/>
    <r:require module="jquery-mobile"/>
    <r:require module="jquerymobileaddons"/>
    <r:require module="prototypeManual"/>
    <g:setProvider library="prototype"/>
    <!-- custom event handling -->
    <r:script disposition="head">

        var olfEvHandler = new OlfEventHandler('OpenLabFrameworkEventHandler');

        function OlfEventHandler(name) {
            this.name = name;
            //Register custom event for changes to fields

            this.propertyValueChangedEvent = new YAHOO.util.CustomEvent("propertyValueChangedEvent", this);
            this.bodyContentChangedEvent = new YAHOO.util.CustomEvent("bodyContentChangedEvent", this);
        }

    </r:script>
    <!-- Layout head (Sitemesh) -->
    <g:layoutHead/>

    <!-- Set javascript provider to prototype -->

    <r:layoutResources/>
    <script type="text/javascript">
        jQuery.noConflict();
    </script>
    <!-- Main CSS at the end to make sure it is effective -->
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}"/>
    <meta name="viewport" content="width=device-width"/>
</head>

<!-- BODY -->
<body>
    <div id="mainPage" data-role="page" data-url="content">
    <g:render template="/layouts/mobile_header"/>
<ul id="suggestions" data-role="listview" data-inset="true" class="ui-listview ui-listview-inset ui-corner-all ui-shadow"></ul>
    <div id="body" role="main">
        <!-- Body -->
        <g:layoutBody/>
    </div>

    <!-- BOTTOM: Footer -->
    <div id="bottom1" data-theme="d" data-role="footer" data-position="fixed">
        <span>
            <g:submitButton name="Logout" action="index" controller="logout"/>
        </span>
        <div style="float:right;">
            Logged in as ${sec.loggedInUserInfo(field:'username')}
            (v. <g:meta name="app.version"/>)
        </div>
    </div>


    </div>
<r:layoutResources/>

</body>

</html>