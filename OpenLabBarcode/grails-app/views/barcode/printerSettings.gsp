    <div class="dialog" id="printerSettings">
    <div id="printerMessage"></div>
        <g:form>
        <table>
            <tbody>            
                <tr class="prop">
                    <td valign="top" class="name">
                        Printer URL:
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: barcodeInstance, field: 'description', 'errors')}">
                        <g:textField name="baseUrl" value="${baseUrl}" />
                    </td>
                </tr>
             </tbody>
          </table>
          <span class="button">
          	<g:submitToRemote action="changeBaseUrl" update="body" name="change" class="save" value="Save URL" />
          	<g:submitToRemote action="testBaseUrl" update="printerMessage" name="test" class="show" value="Test connection" />
          </span>
          </g:form>
      </div>