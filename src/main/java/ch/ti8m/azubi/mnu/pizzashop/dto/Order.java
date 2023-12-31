package ch.ti8m.azubi.mnu.pizzashop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Order {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("dateTime")
    private Timestamp dateTime;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private String address;

    @JsonProperty("pizzaOrders")
    private List<PizzaOrder> pizzaOrders;

    public Order() {
    }
    public Order(Timestamp dateTime, String phone, String address) {
        this.dateTime = dateTime;
        this.phone = phone;
        this.address = address;
    }
    public Order(Timestamp dateTime, String phone, String address, List<PizzaOrder> pizzaOrders) {
        this.dateTime = dateTime;
        this.phone = phone;
        this.address = address;
        this.pizzaOrders = pizzaOrders;
    }

    public Integer getId() {
        return id;
    }
    public Timestamp getDateTime() {
        return dateTime;
    }
    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public List<PizzaOrder> getPizzaOrders() {
        return pizzaOrders;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setPizzaOrders(List<PizzaOrder> pizzaOrders){
        this.pizzaOrders = pizzaOrders;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(id == null){
            return false;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id,order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }
}
