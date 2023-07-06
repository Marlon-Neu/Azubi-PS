const tokenString = "<img class = 'token tokenAdd'>";
const tokenColumn ="1.75em";
const tokenStackColumn = ".25em";
$(function(){
    $(".addPizza").click(function (){
        let currentVal = parseInt($(this).siblings("input").val());
        if(currentVal + 1 <= 25) {
            let newVal = currentVal + 1;
            let amountClass = buildMultiClassSelector($(this).siblings("input").attr("class"));
            $(amountClass).val(newVal);
            let tokenClass = buildMultiClassSelector($(this).parent().siblings(".tokenGrid").attr("class"))
            let tokenContainer = $(tokenClass);
            let oldProperty = $(this).parent().parent().find(tokenContainer).css("grid-template-columns");
            if(oldProperty === "none"){
                oldProperty = "";
            }
            let split = oldProperty.split(" ");

            if(newVal%5 === 0) {
                for(let i=newVal-5;i<newVal-1;i++){
                    split[i] = tokenStackColumn;
                }
            }

            split.push(tokenColumn);
            tokenContainer.css("grid-template-columns",split.join(" "));

            let token = $(tokenString);
            tokenContainer.append(token);
            tokenContainer.find("img:not(.tokenRemove):last").on("animationend",function (){
                $(this).removeClass("tokenAdd");
            });
        }
    });

    $(".removePizza").click(function (){
        let currentVal = parseInt($(this).siblings("input").val());
        if(currentVal - 1 >= 0) {
            let newVal = currentVal - 1;
            let amountClass = buildMultiClassSelector($(this).siblings("input").attr("class"));
            $(amountClass).val(newVal);
            let tokenClass = buildMultiClassSelector($(this).parent().siblings(".tokenGrid").attr("class"))
            let tokenContainer = $(tokenClass);
            let oldProperty = $(this).parent().parent().find(tokenContainer).css("grid-template-columns");
            let split = oldProperty.split(" ");

            if((newVal+1)%5 === 0) {
                for(let i=newVal-4;i<newVal;i++){
                    split[i] = tokenColumn;
                }
            }

            split.pop();

            tokenContainer.css("grid-template-columns",split.join(" "));
            let lastToken = tokenContainer.find("img:not(.tokenRemove):last");
            lastToken.addClass("tokenRemove")
            lastToken.on("animationend",function (){
                $(this).remove();
            });
        }
    });


    $(".orderButton").click(function (){
        $(".overlay").css("visibility","visible")
        for(let i = 0; i < 10;i++){
            let pizza = ".pizzaAmount.row" + i;
            let pizzaAmount = parseInt($(pizza).val());
            if(pizzaAmount !== 0) {
                $(pizza).parent().parent().clone(true,true).appendTo("#overlayPizza");
                let pizzaLabel = $("#overlayPizza").find(".pizzaItem").children("div:first-child");
                let newPizzaLabel = pizzaLabel.html() + "  x" +pizzaAmount;
                pizzaLabel.html(newPizzaLabel);
            }
        }
        if($("#overlayPizza").children().length === 0){
            $(".overlayForm").hide();
            $(".overlayWarning").show();
        }
    });

    $(".cancelButton").click(function (){
        $(".overlay").css("visibility","hidden")
        $("#overlayPizza").children().remove();
        $(".overlayForm").show();
        $(".overlayWarning").hide();
    });

    $(".clearButton").click(clearTokenGrids);

   fillTokenGrids();

});

function tokenStacker(number){
    let gridString = "";
    gridString += ((tokenStackColumn + " ").repeat(4) + tokenColumn + " ").repeat(Math.floor(number/5));
    gridString += (tokenColumn + " ").repeat(number%5);
    return gridString;
}

function fillTokenGrids(){
    let numOfPizzas = $(".pizzaList").not("#overlayPizza").children().length;
    for (let i =0 ; i < numOfPizzas ; i++) {
        let pizzaRow = ".row" + i;
        let pizzaAmount = parseInt($(".pizzaAmount" + pizzaRow).val());
        if (pizzaAmount !== 0){
            let tokenContainer = $(".tokenGrid" + pizzaRow);
            tokenContainer.css("grid-template-columns", tokenStacker(pizzaAmount));
            for (let j = 0; j < pizzaAmount; j++) {
                let token = $(tokenString);
                tokenContainer.append(token);
            }
            tokenContainer.find("img").on("animationend",function (){
                $(this).removeClass("tokenAdd");
            });
        }
    }
}

function clearTokenGrids(){
    let numOfPizzas = $(".pizzaList").not("#overlayPizza").children().length;
    $(".pizzaAmount").val(0);
    for (let i =0 ; i < numOfPizzas ; i++) {
        let pizzaRow = ".row" + i;
        let tokenContainer = $(".tokenGrid" + pizzaRow);
        tokenContainer.children().addClass("tokenRemove");
        tokenContainer.children().on("animationend",function (){
            $(this).remove();
        });
        tokenContainer.css("grid-template-columns",tokenColumn);
    }
}

function buildMultiClassSelector(classString){
    let newString = "";
    let split = classString.split(" ");
    for(let i=0; i < split.length;i++){
        newString += "." + split[i];
    }
    return newString;
}

