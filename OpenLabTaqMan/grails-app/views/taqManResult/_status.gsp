<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <body>

    <h1>TaqManResult Status</h1>
    <table>
        <tr>
            <td>Detectors assigned:<br/>
                (<g:link action="checkDetectors" params="${['taqManResultId': taqManResultInstance?.id, 'attachment.id': taqManResultInstance?.attachment?.id]}">change</g:link>)
            </td> <td><g:ampel bool="${taqManResultInstance.detectorsAssigned}"/></td>
        </tr>
        <tr>
            <td>Samples assigned:<br/>
                (<g:link action="assignSamples" params="${['taqManResultId': taqManResultInstance?.id, 'attachment.id': taqManResultInstance?.attachment?.id]}">change </g:link>)
            </td> <td><g:ampel bool="${taqManResultInstance.samplesAssigned}"/></td>
        </tr>
    </table>
    
    </body>
</html>