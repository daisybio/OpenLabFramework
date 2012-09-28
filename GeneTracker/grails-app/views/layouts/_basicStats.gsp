<head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        function drawChart() {

            var plotChart = function(e, divId){
                var jsonData = e.responseText.evalJSON()

                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsonData);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById(divId));
                chart.draw(data, {width: 400, height: 240});
            };

            <g:remoteFunction controller="stats" action="geneStats" onSuccess="plotChart(e, 'geneStats_div')"/>
            <g:remoteFunction controller="stats" action="genesByUserStats" onSuccess="plotChart(e, 'geneUserStats_div')"/>
            <!-- <g:remoteFunction controller="stats" action="recombinantStats" onSuccess="plotChart(e, 'recombinantStats_div')"/> -->
            <!-- <g:remoteFunction controller="stats" action="cellLineDataStats" onSuccess="plotChart(e, 'cellLineDataStats_div')"/> -->
        }

    </script>
</head>

<body>
    <div class="content"/>
        <table>
            <thead>
                <tr><th>Genes per Project</th><th>Genes per User</th></tr>
            </thead>
            <tr>
                <td><div id="geneStats_div"/></td>
                <td><div id="geneUserStats_div"/></td>
            </tr>
        </table>
    </div>
</body>