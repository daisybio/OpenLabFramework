<html>
	<head>
		<title>OpenLabFramework - BarcodeScanner Interface</title>
		
		<g:javascript library="yui" />
		
		<script type='text/javascript'>
			var timerid;

			function submitToController(value){
				<g:remoteFunction action="ajaxShow" controller="barcode" update="result" params="\'id=\'+value"/>
			}
		
			function timerSubmit(){
			  clearTimeout(timerid);
			  timerid = setTimeout(function() { 
				submitToController(document.forms['inputForm'].elements['scannerInput'].value);
			  	document.forms['inputForm'].elements['barcodeSubmitted'].value = document.forms['inputForm'].elements['scannerInput'].value; 
			  	document.forms['inputForm'].elements['scannerInput'].value = "";
			  }, 500);
			  				  		
			}
		</script>
		
		<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
	</head>
	<body>
		<h1>OpenLabFramework - BarcodeScanner Interface</h1>
		<g:formRemote url="[controller:'barcode',action:'ajaxShow']" update="result" name="inputForm">
			<input type="text" size="30" name="scannerInput" onblur="this.focus()" onkeyup="timerSubmit();">
			<input type="text" readonly="true" size="30" name="barcodeSubmitted">
		</g:formRemote>
		
	<div id="result" style="padding-top:20px;"></div>
	
	<script type='text/javascript'>
		(function(){
		document.forms['inputForm'].elements['scannerInput'].focus();
		}())
	</script>
	</body>
</html>

