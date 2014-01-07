<div id="barcodeCreator">
    <div style="height:150px;">
    <h2>Barcode-Scan</h2>

    <p style="padding: 10px;">In order to read a barcode focus this textfield and scan:</p>

    <g:formRemote url="[controller: 'barcode', action: 'ajaxShow']" update="result" name="inputForm">
        <input type="text" size="30" name="scannerInput" onmouseover="this.focus()" onkeyup="timerSubmit();">
        <input type="text" style="display:none;" readonly="true" size="30" name="barcodeSubmitted">
    </g:formRemote>
    </div>
    <g:if test="${switched}"><div class="buttons" style="padding:5px;"><g:remoteLink controller="barcode"
                                                                                     params="${params}"
                                                                                     update="barcodeCreator"
                                                                                     action="renderBarcodeCreator">
        Switch to Print Dialog</g:remoteLink></div></g:if>

</div>
<script type="text/javascript">
    var updateBarcodeAddin = function (type, args, me) {
        ${remoteFunction(params: "\'name=\'+args[0]+\'&className=\'+args[1]+\'&id=\'+args[2]", controller:"barcode", action:"renderBarcodeCreator", update:[success: "barcodeCreator", failure: "barcodeCreator"])}
    };

    olfEvHandler.bodyContentChangedEvent.subscribe(updateBarcodeAddin, this);

</script>

<script type='text/javascript'>
    var timerid;

    function submitToController(value) {
    <g:remoteFunction action="ajaxShow" controller="barcode" update="body" params="\'id=\'+value+\'&bodyOnly=true\'"/>
    }

    function timerSubmit() {
        clearTimeout(timerid);
        timerid = setTimeout(function () {
            submitToController(document.forms['inputForm'].elements['scannerInput'].value);
            document.forms['inputForm'].elements['barcodeSubmitted'].display = '';
            document.forms['inputForm'].elements['barcodeSubmitted'].value = document.forms['inputForm'].elements['scannerInput'].value;
            document.forms['inputForm'].elements['scannerInput'].value = "";
        }, 500);

    }
</script>
