<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Pizza Shop test</title>
	</head>
	<body>
	<ul>
		<#list pizzaList as pizza>
			<li>
				${pizza.name} : ${pizza.price} CHF
			</li>
		</#list>
	</ul>
	</body>
</html>