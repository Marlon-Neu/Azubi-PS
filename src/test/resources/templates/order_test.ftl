
${dateTime}:
Address : ${address}

Phone Number : ${phone}
<#list pizzaOrders as pizzaOrder>
    ${pizzaOrder.pizza.name} : ${pizzaOrder.pizza.price} CHF * ${pizzaOrder.amount} = ${pizzaOrder.pizza.price * pizzaOrder.amount}
</#list>
