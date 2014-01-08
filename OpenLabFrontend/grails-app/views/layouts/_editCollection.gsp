<g:select class="select2" name="${propertyName}Select" from="${domainClassList}"  optionKey="id"  value="${currentValue?.id}"  noSelection="['':'-None-']"
          onchange="${remoteFunction(controller: targetController, action:'saveEditable',update: targetUpdate, id: id, params:'\'propertyName='+propertyName+'&referencedClassName='+referencedClassName+'&selected=\' + this.value' )}"/>

