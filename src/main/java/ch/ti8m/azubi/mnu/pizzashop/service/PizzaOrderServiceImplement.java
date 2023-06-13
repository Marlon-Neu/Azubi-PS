package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.mnu.pizzashop.persistence.OrderDAO;
import ch.ti8m.azubi.mnu.pizzashop.persistence.PizzaOrderDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class PizzaOrderServiceImplement implements PizzaOrderService
{
    private PizzaOrderDAO pizzaOrderDAO;


    public PizzaOrderServiceImplement() throws SQLException {
        String connectionUrl = "jdbc:mariadb://localhost:3306/ti8m.mnupizzashop?user=PSWebapp&password=pass1234";
        Connection connection = DriverManager.getConnection(connectionUrl);
        pizzaOrderDAO = new PizzaOrderDAO(connection);
    }
    @Override
    public List<PizzaOrder> get(int id) throws Exception {
        List<PizzaOrder> pizzaOrders = pizzaOrderDAO.get(id);
        if(pizzaOrders.isEmpty()){
            throw new NoSuchElementException("No Pizza Orders with order id "+ id +"in Database");
        }
        return pizzaOrders;
    }

    @Override
    public List<List<PizzaOrder>> list() throws Exception {
        return pizzaOrderDAO.list();
    }

    @Override
    public List<PizzaOrder> create(List<PizzaOrder> pizzaOrders) throws Exception {
        if(pizzaOrders.isEmpty()){
            throw new IllegalArgumentException("Pizza Order is required");
        }
        pizzaOrderDAO.create(pizzaOrders);
        return pizzaOrders;
    }

    @Override
    public void update(List<PizzaOrder> pizzaOrders) throws Exception {
        if(pizzaOrders.isEmpty()){
            throw new IllegalArgumentException("Pizza Order is required");
        }
        pizzaOrderDAO.update(pizzaOrders);
    }

    @Override
    public List<PizzaOrder> save(List<PizzaOrder> pizzaOrders) throws Exception {
        if(pizzaOrders.isEmpty()){
            throw new IllegalArgumentException("Pizza Order is required");
        }
        pizzaOrderDAO.save(pizzaOrders);
        return pizzaOrders;
    }

    @Override
    public void remove(int id) throws Exception {
        pizzaOrderDAO.delete(id);
    }
}
