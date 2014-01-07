<div class="list">
  <table width="90%">
  	<tr>
  		<td>Object Type:</td><td>${dataObject.type}</td>
  	</tr>
  	<tr>
  		<td>Name:</td><td>${dataObject}</td>
  	</tr>
  		<g:if test="${dataObject.class.hasProperty('description') && dataObject.description}"><tr><td>Description:</td><td>${dataObject.description}</td></tr></g:if>
  		<g:if test="${dataObject.class.hasProperty('notes') && dataObject.notes}"><tr><td>Notes:</td><td>${dataObject.notes}</td></tr></g:if>
  		<g:if test="${dataObject.class.hasProperty('projects') && dataObject.projects}"><tr><td>Project(s):</td>
  			<td>
  				<ul>
  					<g:each in="${dataObject.projects}" var="item">
  						<li>${item}</li>
  					</g:each>
  				</ul>
			</td></tr>
		</g:if> 
		<g:if test="${storageElements}"><tr><td>Storage(s):</td>
  			<td>
  				<table>
  					<tr><th>Box</th><th>X</th><th>Y</th>
  					<g:each in="${storageElements}" var="item">
  						<tr>
  							<td>${item.box.toString()}</td>
  							<td><g:axisLabel num="${item.xcoord}" axis="x"/></td>
  							<td><g:axisLabel num="${item.ycoord}" axis="y"/></td>
						</tr>
  					</g:each>
  				</table>
			</td></tr>
		</g:if> 
  </table>
</div>