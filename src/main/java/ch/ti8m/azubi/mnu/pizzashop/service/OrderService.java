package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;

import java.util.List;
import java.util.NoSuchElementException;

public interface OrderService {

    Order get(int id) throws NoSuchElementException;

    List<Order> list();

    Order create(Order order) throws IllegalArgumentException;

    void update(Order order) throws IllegalArgumentException;

    void remove(int id);
}
