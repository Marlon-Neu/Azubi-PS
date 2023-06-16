package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DAO<Order>{

    private Connection connection;
    private PizzaOrderDAO pizzaOrderDAO;

    public OrderDAO(Connection connection){
        this.connection = connection;
        pizzaOrderDAO = new PizzaOrderDAO(connection);
    }
    public List<Order> list() throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("SELECT id,date,phone,address from orders")){
            List<Order> orderList = new ArrayList<Order>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setDateTime(resultSet.getTimestamp("date"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setPizzaOrders(pizzaOrderDAO.get(order.getId()));
                orderList.add(order);
            }
            return orderList;
        }
    }

    public Order get(int id) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("SELECT id,date,phone,address from orders where id = ?")){
            Order order = new Order();
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                order.setId(resultSet.getInt("id"));
                order.setDateTime(resultSet.getTimestamp("date"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setPizzaOrders(pizzaOrderDAO.get(order.getId()));
                return order;
            }
            return null;
        }
    }

    public Order create(Order order) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("insert into orders (date, phone" +
                ", address) values (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1,order.getDateTime());
            statement.setString(2,order.getPhone());
            statement.setString(3,order.getAddress());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int generatedId = generatedKeys.getInt(1);
            order.setId(generatedId);
        }
        for(PizzaOrder pizzaOrder : order.getPizzaOrders()){
            pizzaOrder.setOrder_id(order.getId());
        }
        pizzaOrderDAO.create(order.getPizzaOrders());
        return order;
    }

    public void update(Order order) throws Exception {
        if(order.getId() != null) {
            try (PreparedStatement statement = connection.prepareStatement("update orders set date = ?, phone  = ?" +
                    ",address = ? where id = ?")) {
                statement.setTimestamp(1,order.getDateTime());
                statement.setString(2,order.getPhone());
                statement.setString(3,order.getAddress());
                statement.setInt(4,order.getId());
                statement.executeUpdate();
            }
            pizzaOrderDAO.update(order.getPizzaOrders());
        }
    }

    public Order save(Order order) throws Exception {
        if (order.getId() == null){
            return create(order);
        }
        else {
            update(order);
        }
        return order;
    }

    public boolean delete(int id) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("delete from pizza_orders where order_id=?")){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        pizzaOrderDAO.delete(id);
        try (PreparedStatement statement = connection.prepareStatement("delete from orders where id=?")) {
            statement.setInt(1, id);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }
}
