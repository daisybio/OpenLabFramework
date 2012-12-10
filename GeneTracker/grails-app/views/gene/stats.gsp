<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="stats" />
    <title>Open Laboratory Framework</title>
</head>

<body>
    <h1>Gene Stats</h1>

    <div class="content"/>
        Chart type: <g:select name="typeOfChart" value="${chartType?:"piechart"}" from="${['barchart', 'piechart', 'columnchart']}"
                      onchange="${remoteFunction(controller:'gene', action:'stats', update:'body', params:'\'chartType=\' + this.value + \'&bodyOnly=true\'')}"/><br/><br/>
        <table>
            <thead>
                <tr><th>Genes per Project</th><th>Genes per User</th></tr>
            </thead>
            <tr>
                <td><div id="geneStats_div"/></td>
                <td><div id="geneUserStats_div"/></td>
            </tr>
            <tr>
                <td><div id="geneTable_div"></div></td>
                <td><div id="geneUserTable_div"></div></td>
            </tr>
        </table>
    </div>

<script type="text/javascript">
    YAHOO.util.Event.onDOMReady(function(){
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1', {'packages':['corechart', 'table'], 'callback': drawChart});
    // Set a callback to run when the Google Visualization API is loaded.

    function drawChart() {
        if(typeof chartType == "undefined") var chartType = '${chartType?:"piechart"}';

        function plotChart(e, divId, tableDivId, type){
        var jsonData = e.responseText.evalJSON();

        // Create our data table out of JSON data loaded from server.
        var data = new google.visualization.DataTable(jsonData);

        // Instantiate and draw our chart, passing in some options.
        var chart;

        if(type=='piechart')
            chart = new google.visualization.PieChart(document.getElementById(divId));
        else if(type=='columnchart'){
            chart = new google.visualization.ColumnChart(document.getElementById(divId));
        }
        else{
            chart = new google.visualization.BarChart(document.getElementById(divId));
        }
        chart.draw(data, {width: 500, height: 300});
        google.visualization.events.addListener(chart, 'select', chartSelectHandler);

        var table = new google.visualization.Table(document.getElementById(tableDivId));
        table.draw(data, {width: 500, height: 300});

        function chartSelectHandler() {
            table.setSelection(chart.getSelection()) ;
        }

        function tableSelectHandler() {
            chart.setSelection(table.getSelection());
        }
    };
    <g:remoteFunction controller="stats" action="geneStats" onSuccess="plotChart(e, 'geneStats_div', 'geneTable_div', chartType);"/>
    <g:remoteFunction controller="stats" action="genesByUserStats" onSuccess="plotChart(e, 'geneUserStats_div', 'geneUserTable_div', chartType);"/>
    };
    });

</script>

</body>
