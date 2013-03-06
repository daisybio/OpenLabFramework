<h2>Label Preview</h2>

<select id="printersSelect"></select>

<div id="labelImageDiv" style="padding-left:50px; width:200px; height:120px;">
        <img style="
        /* for firefox, safari, chrome, etc. */
        -webkit-transform: rotate(90deg);
        -moz-transform: rotate(90deg);
        /* for ie */
        filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
        " id="labelImage" src="" alt="label preview"/>
</div>

<div class="buttons">
<span><g:remoteLink controller="barcode" params="${params}" update="barcodeCreator" action="renderBarcodeCreator">
Back to print dialog</g:remoteLink></span>
<span class="button"><button id="printButton" onClick="printLabel()" type="button">Print Label</button></span>
</div>

<script type="text/javascript">
   
        var printersSelect = document.getElementById('printersSelect');
        var printButton = document.getElementById('printButton');
        
        // loads all supported printers into a combo box 
        function loadPrinters()
        {
            var printers = dymo.label.framework.getPrinters();
            if (printers.length == 0)
            {
                alert("No DYMO printers are installed. Install a DYMO printer to print.");
                return;
            }

            for (var i = 0; i < printers.length; i++)
            {
                var printer = printers[i];
                if (printer.printerType == "LabelWriterPrinter")
                {
                    var printerName = printer.name;

                    var option = document.createElement('option');
                    option.value = printerName;
                    option.appendChild(document.createTextNode(printerName));
                    printersSelect.appendChild(option);
                }
            }
        }
        
        function updatePreview()
        {
            if (!label)
                return;
            var pngData = label.render();

            var labelImage = document.getElementById('labelImage');
            labelImage.src = "data:image/png;base64," + pngData;
        }
        
        printButton.onclick = function()
        {
            try
            {
                if (!label)
                {
                    alert("Load label before printing");
                    return;
                }

                label.print(printersSelect.value);
            }
            catch(e)
            {
                alert(e.message || e);
            }
        }
        
        try{
        	loadPrinters();
        }catch(e)
        {
        	alert(e);
        }
        
        var labelXml = '${barcodeLabel}';
        var label = dymo.label.framework.openLabelXml(labelXml);
        
        var descr = '${barcodeInstance?.dataObject?.toBarcode()?:barcodeInstance.dataObject.toString()}';
        var splitResult = descr.split("~");

        if(splitResult.length > 0)
        {
        	label.setObjectText("dataOne", splitResult[0]);
        }
        
        if(splitResult.length > 1)
        {
        	label.setObjectText("dataTwo", splitResult[1]);
        }

        else
        {
            label.setObjectText("dataTwo", "")
        }
        
        if(splitResult.length > 2)
        {
        	label.setObjectText("dataThree", splitResult[2]);
        }
        
        else 
        {
        	label.setObjectText("dataThree", "${barcodeInstance?.description}")
        }
        
        label.setObjectText("barcodeValue", "${barcodeText}");
        label.setObjectText("barcodeText", "${barcodeText}");
        //label.setObjectText("dataName", "${barcodeInstance}");
        //label.setObjectText("description", "${barcodeInstance?.description}");
        label.setObjectText("text", "${barcodeInstance?.text}");
        
        updatePreview();
   	
</script>


