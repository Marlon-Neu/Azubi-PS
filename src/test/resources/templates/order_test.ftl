
${dateTime}:
Address : ${address}
Phone Number : ${phone}
<#list pizzaOrders as pizzaOrder>
    <div class="pizzaItem">
        <div class="name">${pizzaOrder.pizza.name}</div>
        <div class="price"> ${pizzaOrder.pizza.price?string.currency} * ${pizzaOrder.amount} = ${(pizzaOrder.pizza.price * pizzaOrder.amount)?string.currency} </div>
    </div>
</#list>