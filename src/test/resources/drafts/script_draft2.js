const tokenString = "<img class = 'token tokenAdd'>";
const tokenColumn ="1.75em";
const tokenStackColumn = ".25em";
const pizzaMax = 25;
const formatter = new Intl.NumberFormat("en-US",{style:"currency",currency:"CHF"});

$(function(){
    const overlayPizza = $("#overlayPizza");
    let timeOut;
    let timeInterval;

    $(".addPizza").click(function (){
        let currentVal = parseInt($(this).siblings(".pizzaAmount").val());
        if(currentVal + 1 <= pizzaMax) {
            let row = buildMultiClassSelector($(this).closest(".pizzaListRow").attr("class"));

            let newVal = currentVal + 1;
            let amountClass = $(row).find(".pizzaAmount");
            amountClass.val(newVal);

            //Changes Overlay Label
            if($(this).closest(".pizzaList").attr("id") === "overlayPizza"){
                let pizzaLabel = overlayPizza.find(row).find(".pizzaItem").find(".name");
                let valDigits= currentVal===0 ? 1 : Math.floor(Math.log(currentVal) * Math.LOG10E + 1);
                let newPizzaLabel = pizzaLabel.html().slice(0,-valDigits) +newVal;
                pizzaLabel.html(newPizzaLabel);
                let priceLabel = overlayPizza.find(row).find(".price");
                let pizzaPrice = parseFloat(overlayPizza.find(row).find(".pizzaPrice").val());
                priceLabel.html(formatter.format(newVal*pizzaPrice));
                updateTotal();
            }

            let tokenGrid = $(row).find(".tokenGrid");
            let oldProperty = tokenGrid.css("grid-template-columns");
            if(oldProperty === "none"){
                oldProperty = "";
            }
            let split = oldProperty.split(" ");

            //"Stacks" Tokens when Necessary
            if(newVal%5 === 0) {
                for(let i=newVal-5;i<newVal-1;i++){
                    split[i] = tokenStackColumn;
                }
            }

            split.push(tokenColumn);
            tokenGrid.css("grid-template-columns",split.join(" "));

            //Adds Token to grid and sets it up for Remove Animation
            let token = $(tokenString);
            tokenGrid.append(token);
            tokenGrid.find("img:not(.tokenRemove):last").on("animationend",function (){
                $(this).removeClass("tokenAdd");
            });
        }
    });

    $(".removePizza").click(function (){
        let currentVal = parseInt($(this).siblings(".pizzaAmount").val());
        if(currentVal - 1 >= 0) {
            let row = buildMultiClassSelector($(this).closest(".pizzaListRow").attr("class"));

            let newVal = currentVal - 1;
            let amountClass = $(row).find(".pizzaAmount");
            amountClass.val(newVal);

            //Changes Overlay Label
            if($(this).closest(".pizzaList").attr("id") === "overlayPizza"){
                let pizzaLabel = overlayPizza.find(row).find(".pizzaItem").find(".name");
                let valDigits= Math.floor(Math.log(currentVal) * Math.LOG10E + 1);
                //valDigits = valDigits===0 ? 1 : valDigits;
                let newPizzaLabel = pizzaLabel.html().slice(0,-valDigits) +newVal;
                pizzaLabel.html(newPizzaLabel);
                let priceLabel = overlayPizza.find(row).find(".price");
                let pizzaPrice = parseFloat(overlayPizza.find(row).find(".pizzaPrice").val());
                priceLabel.html(formatter.format(newVal*pizzaPrice));
                updateTotal();
            }

            let tokenGrid = $(row).find(".tokenGrid");
            let oldProperty = tokenGrid.css("grid-template-columns");
            let split = oldProperty.split(" ");

            //"Unstacks" Stack when necessary.
            if((newVal+1)%5 === 0) {
                for(let i=newVal-4;i<newVal;i++){
                    split[i] = tokenColumn;
                }
            }

            split.pop();
            tokenGrid.css("grid-template-columns",split.join(" "));

            //Animate last untagged token, and removes it.
            let lastToken = tokenGrid.find("img:not(.tokenRemove):last");
            lastToken.addClass("tokenRemove")
            lastToken.on("animationend",function (){
                $(this).remove();
            });
        }
    });

    let circleButton = $(".circleButton");
    circleButton.on("mousedown",function (){
        let button = $(this);
        timeOut = setTimeout(function (){
            timeInterval = setInterval(function (){
                console.log(button);
                button.trigger("click");
            },150);
        },300);
    });

    circleButton.on("mouseup",function (){
        clearTimeout(timeOut);
        clearInterval(timeInterval);
    });
    circleButton.on("mouseout",function (){
        clearTimeout(timeOut);
        clearInterval(timeInterval);
    });


    $(".orderButton").click(function (){
        $(".overlay").css("visibility","visible");
        for(let i = 0; i < 10;i++){
            let row = ".pizzaListRow.row" +i;
            let pizza = $(row).find(".pizzaAmount");
            let pizzaAmount = parseInt(pizza.val());
            if(pizzaAmount !== 0) {
                $(row).clone(true,true).appendTo("#overlayPizza");
                let pizzaLabel = overlayPizza.find(row).find(".name");
                let newPizzaLabel = pizzaLabel.html() + " x" +pizzaAmount;
                pizzaLabel.html(newPizzaLabel);
                let priceLabel = overlayPizza.find(row).find(".price");
                let pizzaPrice = parseFloat(overlayPizza.find(row).find(".pizzaPrice").val());
                priceLabel.html(formatter.format(pizzaAmount*pizzaPrice));
            }
            updateTotal();
        }
        if(overlayPizza.children().length === 0){
            $(".overlayForm").hide();
            $(".overlayWarning").show();
        }
    });

    $(".cancelButton").click(function (){
        $(".overlay").css("visibility","hidden")
        $("#overlayPizza").children(".pizzaListRow").remove();
        //Returns Form and Warning to default
        $(".overlayForm").show();
        $(".overlayWarning").hide();
    });

    $(".clearButton").click(clearTokenGrids);

    //When Refreshed
    fillTokenGrids(); //or clearTokenGrids();
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
        let row = ".pizzaListRow.row" +i;
        let pizza = $(row).find(".pizzaAmount");
        let pizzaAmount = parseInt(pizza.val());

        if (pizzaAmount !== 0){
            let tokenGrid = $(row).find(".tokenGrid");
            tokenGrid.css("grid-template-columns", tokenStacker(pizzaAmount));
            for (let j = 0; j < pizzaAmount; j++) {
                let token = $(tokenString);
                tokenGrid.append(token);
            }
            tokenGrid.find("img").on("animationend",function (){
                $(this).removeClass("tokenAdd");
            });
        }
    }
}

function clearTokenGrids(){
    let numOfPizzas = $(".pizzaList").not("#overlayPizza").children().length;
    $(".pizzaAmount").val(0);
    for (let i =0 ; i < numOfPizzas ; i++) {
        let row = ".pizzaListRow.row" +i;
        let tokenGrid = $(row).find(".tokenGrid");
        tokenGrid.children().addClass("tokenRemove");
        tokenGrid.children().on("animationend",function (){
            $(this).remove();
        });
        tokenGrid.css("grid-template-columns","none");
    }
}

//Used for combining(AND-wise) classes to be used as a selector
function buildMultiClassSelector(classString){
    let newString = "";
    let split = classString.split(" ");
    for(let i=0; i < split.length;i++){
        newString += "." + split[i];
    }
    return newString;
}

function updateTotal(){
    let total = 0;
    $("#overlayPizza").find(".pizzaListRow").each(function(index){
        total += parseInt($(this).find(".pizzaAmount").val()) * parseFloat($(this).find(".pizzaPrice").val());
        console.log(total);
        console.log($(this).find(".pizzaPrice").val())
    });
    $(".total").html("Total : " + formatter.format(total));
}



