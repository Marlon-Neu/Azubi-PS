$(function(){
    let currentPizza = 5;
    $(".addButton").click(function (){
        currentPizza++;
        console.log(currentPizza);
        let tokenContainer = $(".tokenContainer");
        let oldProperty = tokenContainer.css("grid-template-columns");
        console.log(oldProperty);
        let split = oldProperty.split(" ");
        if(currentPizza%5 === 0) {
            for(let i=currentPizza-5;i<currentPizza-1;i++){
                split[i] = "8px";
            }
        }
        split.push("144px");
        console.log(split.join(" "));
        tokenContainer.css("grid-template-columns",split.join(" "));
        let token = $("<img class = 'token tokenAdd'>");
        tokenContainer.append(token);
        tokenContainer.find("img:not(.tokenRemove):last").on("animationend",function (){
            $(this).removeClass("tokenAdd");
        });
    });

    $(".removeButton").click(function (){
        currentPizza--;
        console.log(currentPizza);
        let tokenContainer = $(".tokenContainer");
        let oldProperty = tokenContainer.css("grid-template-columns");
        console.log(oldProperty);
        let split = oldProperty.split(" ");
        if((currentPizza+1)%5 === 0) {
            for(let i=currentPizza-4;i<currentPizza;i++){
                split[i] = "144px";
            }
        }
        split.pop();
        console.log(split.join(" "));
        tokenContainer.css("grid-template-columns",split.join(" "));
        let lastToken = tokenContainer.find("img:not(.tokenRemove):last");
        lastToken.addClass("tokenRemove")
        lastToken.on("animationend",function (){
            $(this).remove();
        });
    });
});