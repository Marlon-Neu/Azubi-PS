<!DOCTYPE html>
<html lang="en">
<head>
    <title>Order ${id}</title>
    <link rel="stylesheet" type="text/css" href="../style2.css">
</head>
<div class = "headers">
    <h3>ti&m Azubi</h3>
    <div class="welcome">
        <h5>Willkommen bei</h5>
        <h1><u>M</u>y <u>N</u>ew <u>U</u>ntitled Pizza Shop</h1>
    </div>
</div>
<body>
<div class="content">
    <div class="order">
        <div class="orderHeader">
            <div class="orderID">Order ${id}:</div>
            <div class="orderDate">um ${dateTime}</div>
        </div>
        <div class="orderInfo">
            <div class="orderAddress"><div class="infoLabel">Addresse</div><div class="info"> ${address}</div></div>
            <div class="orderPhone"><div class="infoLabel">Telefon Nummer</div><div class="info"> ${phone}</div></div>
        </div>
        <div class="pizzaList">
            <div class="pizzaListTitle">Pizzas bestellt:</div>
            <#list pizzaOrders as pizzaOrder>
                <div class="pizzaItem">
                    <div class="name">${pizzaOrder.pizza.name}</div>
                    <div class="price"> ${pizzaOrder.pizza.price?string.currency} * ${pizzaOrder.amount} = ${(pizzaOrder.pizza.price * pizzaOrder.amount)?string.currency} </div>
                </div>
            </#list>
        </div>
    </div>
</div>
</body>
</html>