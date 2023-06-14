package ch.ti8m.azubi.mnu.pizzashop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class Pizza {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private BigDecimal price;

    public Pizza(){

    }
    public Pizza(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(id == null){
            return false;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Pizza pizza = (Pizza) o;
        return Objects.equals(id,pizza.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }


}
