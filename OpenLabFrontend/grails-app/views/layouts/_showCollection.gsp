<div onclick="${g.remoteFunction(action:'updateEditable',
                                                       id: id,
                                                       params:[referencedClassName: referencedClassName.toString(),
                                                       propertyName: propertyName.toString()],
                                                       update: propertyName +'Editable')}"
>
    <g:if test="${newValue}">${newValue}</g:if>
    <g:else><img src="${resource(dir:'images/skin',file:'olf_tool_small.png')}"/></g:else>

</div>