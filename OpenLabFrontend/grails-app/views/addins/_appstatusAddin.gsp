<style type="text/css" media="screen">
.status {
    background-color: #eee;
    border: .2em solid #fff;
    margin: 0em 2em 1em;
    padding: 1em;
    width: 12em;
    float: left;
    -moz-box-shadow: 0px 0px 1.25em #ccc;
    -webkit-box-shadow: 0px 0px 1.25em #ccc;
    box-shadow: 0px 0px 1.25em #ccc;
    -moz-border-radius: 0.6em;
    -webkit-border-radius: 0.6em;
    border-radius: 0.6em;
}

.ie6 .status {
    display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
}

.status ul {
    font-size: 0.9em;
    list-style-type: none;
    margin-bottom: 0.6em;
    padding: 0;
}

.status li {
    line-height: 1.3;
}

.status h1 {
    text-transform: uppercase;
    font-size: 1.1em;
    margin: 0 0 0.3em;
}
</style>
<div class="status" id="status_${slot}">
    <h1>Application Status</h1>
    <ul>
        <li>App version: <g:meta name="app.version"/></li>
        <li>Grails version: <g:meta name="app.grails.version"/></li>
        <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
        <li>JVM version: ${System.getProperty('java.version')}</li>
        <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>   <br/>
    </ul>
</div>