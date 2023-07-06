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
            <div class="orderDate">at ${dateTime}</div>
        </div>
        <div class="orderInfo">
            <div class="orderAddress">Address : ${address}</div>
            <div class="orderPhone">Phone Number : ${phone}</div>
        </div>
        <div class="pizzaList test">
            <div class="pizzaListTitle test">Pizzas Ordered:</div>
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