<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <g:setProvider library="jquery"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
<title>Open Laboratory Framework</title>
</head>

<body>
    <h1>Gene Stats</h1>

    <div class="content"/>
        Chart type: <g:select name="typeOfChart" from="${['barchart', 'piechart', 'columnchart']}" value="piechart" onchange="chartType=this.value; drawChart();" /><br/><br/>
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


<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
        var chartType = "piechart";

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1', {'packages':['corechart', 'table']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        function drawChart() {

            var plotChart = function(e, divId, tableDivId, type){
                var jsonData = e.responseText.evalJSON()

                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsonData);

                // Instantiate and draw our chart, passing in some options.
                if(type=='piechart')
                    var chart = new google.visualization.PieChart(document.getElementById(divId));
                else if(type=='columnchart'){
                    var chart = new google.visualization.ColumnChart(document.getElementById(divId));
                }
                else{
                    var chart = new google.visualization.BarChart(document.getElementById(divId));
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

        <g:remoteFunction controller="stats" action="geneStats" onSuccess="plotChart(e, 'geneStats_div', 'geneTable_div', chartType)"/>
        <g:remoteFunction controller="stats" action="genesByUserStats" onSuccess="plotChart(e, 'geneUserStats_div', 'geneUserTable_div', chartType)"/>
        }


</script>
</body>
