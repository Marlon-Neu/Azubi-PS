package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


class OrderServiceImplementTest {

    @Test
    void get() throws Exception {
        OrderServiceImplement orderServ = new OrderServiceImplement();
        Order order = orderServ.get(1);
        assert(order.getId().equals(1));
    }

    @Test
    void list() throws Exception{
        OrderServiceImplement orderServ = new OrderServiceImplement();
        List<Order> orderList = orderServ.list();
        assert(orderList.get(0).getId().equals(1));
        for (Order order :orderList){
            System.out.println(order.getId() + ": " +order.getAddress() +", "+order.getPhone() +", " +order.getDateTime());
            for(PizzaOrder pizzaOrder : order.getPizzaOrders()){
                System.out.println("-->" + pizzaOrder.getPizza().getName() + " *"+pizzaOrder.getAmount());
            }
        }
    }

    @Test
    void saveAndRemove() throws Exception{
        OrderServiceImplement orderServ = new OrderServiceImplement();
        Order testOrder = orderServ.get(1);
        testOrder.setId(null);
        testOrder.setDateTime(Timestamp.valueOf(LocalDateTime.now()));

        String newPhone = "0123456789";
        orderServ.save(testOrder);
        assert (testOrder.getId() != null);
        Integer testId = testOrder.getId();
        testOrder.setPhone(newPhone);
        orderServ.save(testOrder);
        assert (orderServ.get(testId).getPhone().equals(newPhone));
        orderServ.remove(testId);
        try{
            orderServ.get(testId);
        }catch (NoSuchElementException e){
            System.out.println("REMOVE SUCCESSFUL: "+ e);
        }
    }
}