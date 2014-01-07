<g:if test="${barcode}">
	<li>
		<g:remoteLink controller="barcode" action="print" id="${barcode.id}" onFailure="alert('Print job could not be sent to printer!');" onSuccess="alert('Print job sent to label printer!');">
			Print previous label 
		</g:remoteLink>
	</li>
</g:if>