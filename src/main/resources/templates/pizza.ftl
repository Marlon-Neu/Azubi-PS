<!DOCTYPE html>
<html>
<head>
    <title>Pizzas</title>
    <link rel="stylesheet" type="text/css" href="style2.css">
    <script src="jquery-3.7.0.js"></script>
    <script src="script.js"></script>
</head>
<div class = "headers">
    <h3>ti&m Azubi</h3>
    <div class="welcome">
        <h5>Willkommen bei</h5>
        <h1><u>M</u>y <u>N</u>ew <u>U</u>ntitled Pizza Shop</h1>
    </div>
</div>
<body>
    <div class = "content">
        <div class = "pizzaList">
            <div class="pizzaListTitle">Unsere Pizzas</div>
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
        <div class="listButtons">
            <button class="orderButton">Bestellen</button>
            <button class="clearButton">Clear</button>
        </div>
    </div>
    <div class='overlay'>
        <div class='overlayContent'>
            <form class="overlayForm" action="order" method="post">
                <div class = 'pizzaList' id='overlayPizza'>
                    <div class="pizzaListTitle">Current Order: </div>

                </div>
                <div class="formArea">
                    <div class="formRow">
                        <label for="address">Addresse:</label>
                        <input type="text" name="address" id="address" required>
                    </div>
                    <div class="formRow">
                        <label for="phone">Telefon:</label>
                        <input type="text" name="phone" id="phone" required>
                    </div>
                    <label class="total"></label>
                    <input type="submit" value="Order">
                </div>
            </form>
            <div class="overlayWarning" hidden="hidden">Mindestens eine Pizza ist erforderlich!</div>
            <button class="cancelButton">&times;</button>
        </div>
    </div>
</body>
</html>