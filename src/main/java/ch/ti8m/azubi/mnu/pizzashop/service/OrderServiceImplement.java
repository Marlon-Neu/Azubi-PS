package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.persistence.OrderDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderServiceImplement implements OrderService{
    private OrderDAO orderDAO;

    public OrderServiceImplement() throws SQLException {
        Connection connection = MDBConnectionFactory.mariaDBConnection();
        orderDAO = new OrderDAO(connection);
    }
    @Override
    public Order get(int id) throws Exception {
        Order order = orderDAO.get(id);
        if(order == null){
            throw new NoSuchElementException("No order with id "+id+" in the database.");
        }
        return order;
    }

    @Override
    public List<Order> list() throws Exception{
        return orderDAO.list();
    }

    @Override
    public Order create(Order order) throws Exception {
        if(order == null){
            throw new IllegalArgumentException("Order is required");
        }
        orderDAO.create(order);
        return order;
    }
    @Override
    public void update(Order order) throws Exception {
        if(order == null){
            throw new IllegalArgumentException("Order is required");
        }
        if(order.getId() == null){
            throw new IllegalArgumentException("Order ID is required");
        }
        orderDAO.update(order);
    }
    @Override
    public Order save(Order order) throws Exception {
        if(order == null){
            throw new IllegalArgumentException("Order is required");
        }
        orderDAO.save(order);
        return order;
    }

    @Override
    public void remove(int id) throws Exception{
        orderDAO.delete(id);
    }
}
