<ul>
    <g:each in="${storageElts}" var="storageElt">
        <li>
            <div id="divFor${storageElt.id}">
                ${storageElt}: <br/>
                Box: ${storageElt.box}
                X: ${storageElt.xcoord}
                Y: ${storageElt.ycoord}
                <g:remoteLink data-role="button" class="ui-button" controller="storage" action="removeWithMobile" id="${storageElt.id}" update="divFor${storageElt.id}">
                    Remove
                </g:remoteLink>
            </div>
        </li>
    </g:each>
</ul>