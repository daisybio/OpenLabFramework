<g:if test="${taqManSets}">
    <h1>This dataset can be found in the following sets:</h1>
    <ul style="margin-left:50px;"><g:each in="${taqManSets}" var="taqManSet">
        <li>${taqManSet}</li>
    </g:each></ul>
</g:if>

<g:include action="addToSet" id="${taqManResultInstance.id}"/>