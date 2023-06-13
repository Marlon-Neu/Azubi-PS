package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;

import java.util.List;

public interface OrderService {

    Order get(int id) throws Exception;

    List<Order> list() throws Exception;

    Order create(Order order) throws Exception;

    void update(Order order) throws Exception;

    Order save(Order order) throws Exception;

    void remove(int id) throws Exception;
}
