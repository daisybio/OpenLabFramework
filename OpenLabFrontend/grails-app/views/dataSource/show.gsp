<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <g:setProvider library="prototype"/>
    <meta name="layout" content="${params.bodyOnly?'body':'main'}" />
    
    <title>Database Configuration</title>
</head>

<body>
	<div class="body">
	<h1>Database Configuration</h1>
	
	<table>	
		<tbody>
			<tr class="odd">
				<td>URL</td><td>${url}</td>
			</tr>
			<tr class="even">
				<td>Driver</td><td>${driverClassName}</td>
			</tr>
			<tr clss="odd">
				<td>Username</td><td>${username}</td>
			</tr>
		</tbody>
	</table>
	</div>
</body>