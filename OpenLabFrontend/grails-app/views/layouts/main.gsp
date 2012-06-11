<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
 "http://www.w3.org/TR/html4/strict.dtd">
<html>

<!-- HEAD -->
<head>
    <!-- Title -->
    <title><g:layoutTitle default="OpenLaboratoryFramework"/></title>

    <!-- CSS resources -->
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'yui_reset_grid.css')}" type="text/css">
    <!-- Fonts CSS - Recommended but not required -->
    <!-- <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'yui_fonts_min.css')}> -->
    <!-- Core + Skin CSS -->
    <g:loadIcon/>
    <!-- <link rel="shortcut icon" href="${resource(dir: 'images', file: 'openlab_ico.gif')}" type="image/x-icon" /> -->

    <!-- JS resources -->
    <export:resource/>
    <resource:portlet/>
    <resource:treeView/>
    <modalbox:modalIncludes/>
    <ofchart:resources/>
    <filterpane:includes/>
    <gui:resources
            components="['autoComplete', 'richEditor', 'dialog', 'tabView', 'menu', 'dataTable', 'accordion', 'toolTip', 'expandablePanel', 'datePicker']"/>

    <!-- Additional YUI JS files: -->
    <yui:javascript dir="calendar" file="calendar-min.js"/>
    <yui:javascript dir="layout" file="layout-min.js"/>
    <yui:javascript dir="resize" file="resize-min.js"/>

    <!-- Layout head (Sitemesh) -->
    <g:layoutHead/>

    <!-- Main CSS at the end to make sure it is effective -->
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'yui_override.css')}">

    <g:javascript library="prototype"/>
    <g:javascript library="scriptaculous"/>
    <g:javascript src="DYMO.Label.Framework.latest.js"/>

    <g:if test="${params.bodyOnly != 'true'}">
        <script type="text/javascript">


            var olfEvHandler = new OlfEventHandler('OpenLabFrameworkEventHandler');

            function OlfEventHandler(name) {
                this.name = name;
                //Register custom event for changes to fields

                this.propertyValueChangedEvent = new YAHOO.util.CustomEvent("propertyValueChangedEvent", this);
                this.bodyContentChangedEvent = new YAHOO.util.CustomEvent("bodyContentChangedEvent", this);
            }

        </script>
    </g:if>
</head>

<!-- BODY -->
<body class="yui-skin-sam">
<!-- Full-part (non-AJAX-load) -->
<g:if test="${params.bodyOnly != 'true'}">

    <!-- Invisible spinner-->
    <div id="spinner" class="spinner" style="display:none;">
        <img src="${createLinkTo(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
    </div>

    <!-- TOP -->
    <div id='top1'>
        <sec:ifLoggedIn>
            <g:render template="/layouts/header"/>
        </sec:ifLoggedIn>
    </div>

    <!-- CENTER -->
    <div id="center1">
        <div id="body">
            <!-- Body -->
            <g:layoutBody/>
        </div>
    </div>

    <!-- RIGHT: Addins -->
    <sec:ifLoggedIn>
        <div id='right1' align="center">
            <richui:portlet views='[1, 2, 3, 4, 5]' controller="addin" action="swapPositions" readOnly='true'>
                <!-- Taglib providing addins -->
                <addin:layoutAddins controller="${session.bodyController ? params.bodyController : params.controller}"
                                    numberOfViews="${numberOfAddins ? numberOfAddins : 4}"/>
            </richui:portlet>
        </div>

        <!-- LEFT: Project Tree -->

        <div id="left1">
            <g:include controller="projectTree" action="renderTree" params="[id: params.id]"/>
        </div>
    </sec:ifLoggedIn>

    <!-- BOTTOM: Footer -->
    <div id="bottom1">Version <g:meta name="app.version"/> on Grails <g:meta name="app.grails.version"/>
    </div>

    <!-- JS -->
    <script type="text/javascript">
        YAHOO.util.Event.onDOMReady(function () {

            var layout = new YAHOO.widget.Layout({
                units:[
                    { position:'top', height:150, body:'top1', zIndex:1, scroll:null },
                    { position:'right', header:'Addins', width:300, resize:false, collapse:true, scroll:true, body:'right1', animate:true, gutter:'5' },
                    { position:'bottom', height:28, body:'bottom1' },
                    { position:'left', header:'Project Tree', width:200, body:'left1', gutter:'5', resize:true, scroll:true, animate:true, collapse:true},
                    { position:'center', body:'center1', gutter:'5 0', scroll:true }
                ]
            });

            layout.render();
        });

    </script>


    <script type="text/javascript">
        document.observe("dom:loaded", function () {


            Ajax.Responders.register({
                onCreate:function () {
                    if (Ajax.activeRequestCount == 1) {
                        Effect.Appear('loading');
                        //new Effect.toggle('loading', 'appear');
                        //new Effect.Opacity('body', { from: 1.0, to: 0.3, duration: 0.7 });
                        Effect.Appear('blocker');
                    }
                },
                onComplete:function () {
                    if (Ajax.activeRequestCount == 0) {
                        Effect.Fade('loading');
                        Effect.SwitchOff('blocker');
                        //new Effect.toggle('loading', 'appear');
                        //new Effect.Opacity('body', { from: 0.3, to: 1, duration: 0.7 });
                    }
                }
            });
        });
    </script>

    <!-- Script for modifying prototype's request header: finally defeat IE caching -->
    <!-- <script type="text/javascript">
        Ajax.Request.prototype.setRequestHeaders =
         Ajax.Request.prototype.setRequestHeaders.wrap(
           function(original) {
               this.transport.setRequestHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
               this.transport.setRequestHeader("If-Modified-Since", "Thu, 1 Jan 1970 00:00:00 GMT");
             original() // then call the original version
         })
     </script>-->

        <!--  JS for Project Tree -->
    <script type="text/javascript">
        var checkUpdateProjectTree = function (type, args, me) {
            //alert(args[0]);
            var myTree = YAHOO.widget.TreeView.getTree('tree');
            var selectedNode = myTree.getNodeByProperty('selected', true);
            var eventNode = myTree.getNodeByProperty('label', args[0])
            if (selectedNode != eventNode || eventNode == null) {
                updateProjectTree();
            }
        };

        var updateProjectTree = function () {
            ${remoteFunction(controller:"projectTree", action:"renderTree", update:[success: "projectTree", failure: "projectTree"])}
        };

        //olfEvHandler.propertyValueChangedEvent.subscribe(checkUpdateProjectTree, this);
        //olfEvHandler.bodyContentChangedEvent.subscribe(checkUpdateProjectTree, this);
    </script>

</g:if>
<g:else>
    <g:if test="${session.backController && !params.backLink}">
        <div class="backbuttonDiv">
            <span class="backbutton">
                <g:remoteLink controller="${session.backController}" action="${session.backAction}"
                              id="${session.backId}" update="[success: 'body']" params="'bodyOnly=true&backLink=true'">
                    <img src="${createLinkTo(dir: 'images/skin', file: 'olf_back.png')}" alt="Go Back"/>
                </g:remoteLink>
            </span>
        </div>
    </g:if>
    <g:layoutBody/>
</g:else>
</body>
</html>