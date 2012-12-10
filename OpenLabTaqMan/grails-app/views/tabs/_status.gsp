<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <body>

    <h1>TaqManResult Status</h1>
    <table>
        <tr>
            <td>Detectors assigned:<br/>
                (<g:remoteLink update="body" action="checkDetectors" params="${['bodyOnly':true, 'taqManResultId': taqManResultInstance?.id, 'attachment.id': taqManResultInstance?.attachment?.id]}">change</g:remoteLink>)
            </td> <td><g:ampel bool="${taqManResultInstance.detectorsAssigned}"/></td>
        </tr>
        <tr>
            <td>Samples assigned:<br/>
                (<g:remoteLink update="body" action="assignSamples" params="${['bodyOnly':true,'taqManResultId': taqManResultInstance?.id, 'attachment.id': taqManResultInstance?.attachment?.id]}">change </g:remoteLink>)
            </td> <td><g:ampel bool="${taqManResultInstance.samplesAssigned}"/></td>
        </tr>
    </table>
    
    </body>
</html>