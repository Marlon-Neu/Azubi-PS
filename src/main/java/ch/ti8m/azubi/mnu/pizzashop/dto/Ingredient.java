package ch.ti8m.azubi.mnu.pizzashop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
