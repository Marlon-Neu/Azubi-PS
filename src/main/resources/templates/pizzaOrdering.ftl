<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Pizza</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script>

        $(function(){
            let requiredCheckboxes = $('.pizzas :checkbox[required]');
            requiredCheckboxes.change(function(){
                if(requiredCheckboxes.is(':checked')) {
                    requiredCheckboxes.removeAttr('required');
                } else {
                    requiredCheckboxes.attr('required', 'required');
                }
                $(this).parent().find(".pizzaAmount").toggleClass('pizzaAmountShow');
            });
            $(':checkbox:checked').parent().find(".pizzaAmount").toggleClass('pizzaAmountShow');
        });

    </script>
</head>
<body>

    <div class = "headers">
        <h3>ti&m Azubi</h3>
        <div class="welcome">
            <h5>Willkommen bei</h5>
            <h1><u>M</u>y <u>N</u>ew <u>U</u>ntitled Pizza Shop</h1>
        </div>
    </div>
    <div class = "content">
        <div class  = "orderForm">
            <form action="order" method="post">
                <label for="address">Address:</label>
                <input type="text" name="address" id="address" required>
                <label for="phone">Phone:</label>
                <input type="text" name="phone" id="phone" required>
                <div class="form-group pizzas">
                    <#list pizzaList as pizza>
                    <div>
                        <input type="checkbox" id="pizza${pizza_index}" name="pizza[]" value="${pizza.id}" required>
                        <label for="pizza${pizza_index}">${pizza.name}: CHF ${pizza.price}</label>
                        <label for="pizzaAmount${pizza_index}" class="pizzaAmount">  Amount :</label>
                        <input type="number" id="pizzaAmount${pizza_index}" name="pizzaAmount${pizza.id}" value="1" min="1" max="10" class="pizzaAmount">
                    </div>
                    </#list>
                </div>
                <input type="submit" value="Order">
            </form>
        </div>
    </div>
</body>
</html>