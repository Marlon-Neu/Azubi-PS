package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

class PizzaServiceImplementTest {

    @Test
    void get() throws Exception{
        PizzaServiceImplement pizzaServ = new PizzaServiceImplement();
        Pizza pizza = pizzaServ.get(1);
        assert (pizza.getId().equals(1));
    }

    @Test
    void list() throws Exception{
        PizzaServiceImplement pizzaServ = new PizzaServiceImplement();
        List<Pizza> pizzaList= pizzaServ.list();
        assert (pizzaList.get(0).getId().equals(1));
        for(Pizza pizza : pizzaList){
            System.out.println(pizza.getName() + ", " + pizza.getPrice());
        }
    }

    @Test
    void saveAndRemove() throws Exception{
        PizzaServiceImplement  pizzaServ = new PizzaServiceImplement();
        Pizza testPizza = new Pizza("TestPizza",new BigDecimal("19.99"));
        String newName = "TesterPizza";

        pizzaServ.save(testPizza);
        assert (testPizza.getId() != null);
        testPizza.setName(newName);
        pizzaServ.save(testPizza);
        assert (pizzaServ.get(testPizza.getId()).getName().equals(newName));
        pizzaServ.remove(testPizza.getId());
        try {
            pizzaServ.get(testPizza.getId());
        }catch (NoSuchElementException e){
            System.out.println("REMOVE SUCCESSFUL: "+ e);
        }
    }
}