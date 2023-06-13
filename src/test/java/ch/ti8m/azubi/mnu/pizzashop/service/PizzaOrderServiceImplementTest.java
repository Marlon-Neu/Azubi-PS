package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

class PizzaOrderServiceImplementTest {

    @Test
    void get() throws Exception{
        PizzaOrderServiceImplement pizzaOrderServ = new PizzaOrderServiceImplement();
        List<PizzaOrder> pizzaOrders = pizzaOrderServ.get(1);
        assert (pizzaOrders.get(0).getOrder_id().equals(1));
    }

    @Test
    void list() throws Exception{
        PizzaOrderServiceImplement pizzaOrderServ = new PizzaOrderServiceImplement();
        List<List<PizzaOrder>> pizzaOrdersList = pizzaOrderServ.list();
        assert(pizzaOrdersList.get(0).get(0).getOrder_id().equals(1));
        for(List<PizzaOrder> pizzaOrders : pizzaOrdersList){
            for (PizzaOrder pizzaOrder : pizzaOrders){
                System.out.println(pizzaOrder.getOrder_id() +": "+pizzaOrder.getPizza().getName()+" *"+pizzaOrder.getAmount());
            }
        }
    }
}