package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;

import java.util.List;
import java.util.NoSuchElementException;

public interface PizzaOrderService {
    PizzaOrder get(int id) throws NoSuchElementException;

    List<PizzaOrder> list();

    PizzaOrder create(PizzaOrder pizzaOrder) throws IllegalArgumentException;

    void update(PizzaOrder order) throws IllegalArgumentException;

    void remove(int id);
}
