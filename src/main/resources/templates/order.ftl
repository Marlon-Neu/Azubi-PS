<!DOCTYPE html>
<html>
<head>
    <title>Order ${id}</title>
</head>
<body>

<div class="order">
    ${dateTime}
    <br>
    <br>
    Address : ${address}
    <br>
    Phone Number : ${phone}
    <ul>
        <#list pizzaOrders as pizzaOrder>
            <li>
                ${pizzaOrder.pizza.name} : ${pizzaOrder.pizza.price} CHF * ${pizzaOrder.amount} = ${pizzaOrder.pizza.price * pizzaOrder.amount}
            </li>
        </#list>
    </ul>
</div>

</body>
</html>