package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;

import java.util.List;

public interface PizzaOrderService {
    List<PizzaOrder> get(int id) throws Exception;

    List<List<PizzaOrder>> list() throws Exception;

    List<PizzaOrder> create(List<PizzaOrder> pizzaOrders) throws Exception;

    void update(List<PizzaOrder> pizzaOrders) throws Exception;

    List<PizzaOrder> save(List<PizzaOrder> pizzaOrders) throws Exception;

    void remove(int id) throws Exception;
}
