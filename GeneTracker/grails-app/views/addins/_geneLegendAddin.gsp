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
    <h1>Legend</h1>
    <ul>
        <li>W: Wildtype</li>
        <li>M: Mutant</li>
        <li>P: Promotor</li>
        <li>C: cDNA derived</li>
        <li>F: Gene Fragment</li>
        <li>K: Knockdown (Silenced)</li><br/>
    </ul>
</div>
