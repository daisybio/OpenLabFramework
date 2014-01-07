<p><g:select noSelection="['null': 'Select cellLineData']"
             onChange="${remoteFunction(action: 'updateSelectedCellLineData', update: 'ajaxSelectTaqMan', params: '\'selectedCellLineData=\'+this.value')}"
             name="selectedCellLineData.id" from="${cellLineData.sort()}" optionKey="id" value=""/></p><br/>