Inducer: <g:select name="inducer_${sampleName.replace('+', '_')}" optionKey="id" noSelection="${[null: '']}" from="${inducers}"/>

<div style="width:300px; float:right;"><gui:autoComplete
        minQueryLength="3"
        queryDelay="0.5"
        id="assignedSample_${sampleName.replace('+', '_')}"
        resultName="results"
        labelField="label"
        idField="id"
        controller="taqManResult"
        action="searchCellLineDataAsJSON"/></div>


