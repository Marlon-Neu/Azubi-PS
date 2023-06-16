<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Pizza</title>
    <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function(){
            $(function(){
                let requiredCheckboxes = $('.pizzas :checkbox[required]');
                requiredCheckboxes.change(function(){
                    if(requiredCheckboxes.is(':checked')) {
                        requiredCheckboxes.removeAttr('required');
                    } else {
                        requiredCheckboxes.attr('required', 'required');
                    }
                });
            });
        });
    </script>
</head>
<body>

<div class = "pizzaList">
    <ul>
        <#list pizzaList as pizza>
            <li>
                ${pizza.name} : ${pizza.price} CHF
            </li>
        </#list>
    </ul>
</div>
<br>
<div class  = "orderForm">
    <form action="order" method="post">
        <label for="address">Address:</label><br>
        <input type="text" name="address" id="address" required><br>
        <label for="phone">Phone:</label><br>
        <input type="text" name="phone" id="phone" required><br>
        <div class="form-group pizzas">
            <#list pizzaList as pizza>
                <input type="checkbox" id="pizza${pizza_index}" name="pizza[]" value="${pizza.id}" required>
                <label for="pizza${pizza_index}">${pizza.name} : ${pizza.price} CHF </label>
                <label for="pizzaAmount${pizza_index}">  Amount : </label>
                <input type="number" id="pizzaAmount${pizza_index}" name="pizzaAmount${pizza_index}" value="1" min="1" max="10">
                <br>
            </#list>
        </div>
        <input type="submit" value="Order">
    </form>
</div>
</body>
</html>