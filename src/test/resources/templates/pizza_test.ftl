<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Pizza Shop test</title>
	</head>
	<body>

	<div class = "pizzaList">
		<#list pizzaList as pizza>
		<div class="pizzaListRow row${pizza_index}" >
			<div class="tokenGrid"></div>
			<div class="pizzaItem" id="pizzaItem${pizza_index}">
				<div class ="name">${pizza.name}</div>
				<div class ="price">${pizza.price?string.currency}</div>
				<input type="hidden" class="pizzaPrice" name="pizzaPrice[]" value=${pizza.price}>
				<div class="break"></div>
				<div class="ingredientList"> <#list pizza.ingredients as ingredient>${ingredient.name}<#sep>, </#list> </div>
			</div>
			<div class="adjustButtons">
				<button class="circleButton addPizza" type="button"> + </button>
				<button class="circleButton removePizza" type="button"> - </button>
				<input type="hidden" class="pizzaAmount" name="pizzaAmount[]" value="0" >
				<input type="hidden" class="pizzaID" name="pizzaID[]" value="${pizza.id}">
			</div>
		</div>
		</#list>
	</div>

	</body>
</html>