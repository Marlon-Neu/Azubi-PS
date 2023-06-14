package ch.ti8m.azubi.mnu.pizzashop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PizzaOrder {
    @JsonProperty("order_id")
    private Integer order_id;
    @JsonProperty("pizza")
    private Pizza pizza;
    @JsonProperty("amount")
    private Integer amount;

    public PizzaOrder(){

    }
    public PizzaOrder(Integer orderId, Pizza pizza, Integer amount) {
        order_id = orderId;
        this.pizza = pizza;
        this.amount = amount;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
