<g:if test="${compartmentList?.size() > 0}">
<div style="padding:20px;">
    <g:select from="${compartmentList}" name="compartmentSelect"
          optionKey="description"
          optionValue="description"
          noSelection="['':'']"
          onchange="${remoteFunction(action:'updateBoxList', update:'boxListArea', params:'\'compartment=\'+this.value+\'&storageType=\'+document.getElementById(\'storageTypeSelect\').value')}"
/>
</div>
</g:if>
<g:else>
    Please create a compartment first.
</g:else>