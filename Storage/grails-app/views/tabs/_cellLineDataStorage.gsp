<div id="subDataStorageTab">
    Please select a passage: <g:select name="passageSelected" value="${params.subDataObj}" noSelection="['':'']"
              optionKey="id" from="${org.openlab.genetracker.Passage.findAllByCellLineData(org.openlab.genetracker.CellLineData.get(params.id), [sort:'date', order: 'desc'])}"
              onchange="${remoteFunction(update: 'storageForPassageDiv', controller: 'storage', action:'storageTabForPassage', id: params.id, params: '\'subDataObj=\'+ this.value')}"/>
    <g:remoteLink update="subDataStorageTab" controller="storage" action="storageTabWithSubDataObj" id="${params.id}">Refresh</g:remoteLink>
    <div id="storageForPassageDiv">

        <g:if test="${!params.subDataObj}"><div class="message">Showing all passages for this CellLineData. Please select a specific passage before you add something.</div></g:if>
        <g:render template="/tabs/storage"/>

    </div>
</div>