package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {

    @BeforeEach
    void setUp() throws  Exception{
        try (Connection conn = ConnectionFactory.testConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists pizza_orders");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists orders");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists pizza");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("create table pizza (\n" +
                        " id int not null auto_increment,\n" +
                        " name VARCHAR(64) not null,\n" +
                        " price DECIMAL(9,2) not null,\n" +
                        " primary key (id))");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("create table orders (\n" +
                        " id int not null auto_increment,\n" +
                        " date datetime not null,\n" +
                        " phone varchar(20) not null,\n" +
                        " address varchar(50),\n"+
                        " primary key (id))");
            }


            try (Statement statement = conn.createStatement()) {
                statement.execute("create table pizza_orders (" +
                        "order_id int not null," +
                        "pizza_id int not null," +
                        "amount int not null," +
                        "foreign key (order_id) references orders (id)," +
                        "foreign key (pizza_id) references pizza (id))");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("insert into pizza (name,price) values ('Pizza1',10)");
                statement.execute("insert into pizza (name,price) values ('Pizza2',11)");
                statement.execute("insert into pizza (name,price) values ('Pizza3',12)");
            }
        }
    }

    @Test
    void create() throws  Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,03,2,20)),"0777777777","TestAddress 1, 1111 TestOrt");
            order.setPizzaOrders(getTestPizzaOrderSet(2,2));

            orderDAO.create(order);
            assert (order.getId() != null);
            assert (order.getId() == 1);
        }
    }
    @Test
    void list() throws  Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order1 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,03,2,20)),"0777777777","TestAddress 1, 1111 TestOrt");
            Order order2 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,05,12,30)),"0888888888","TestAddress 2, 1111 TestOrt");
            Order order3 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,07,22,40)),"0999999999","TesterAddress 3, 1111 TestOrt");
            order1.setPizzaOrders(getTestPizzaOrderSet(2,3));
            order2.setPizzaOrders(getTestPizzaOrderSet(4,3));
            order3.setPizzaOrders(getTestPizzaOrderSet(2,3));

            orderDAO.create(order1);
            orderDAO.create(order2);
            orderDAO.create(order3);
            List<Order> orderList = orderDAO.list();
            assert (orderList.get(0).getDateTime().equals(order1.getDateTime()));
            assert (orderList.get(1).getPhone().equals(order2.getPhone()));
            assert (orderList.get(2).getAddress().equals(order3.getAddress()));
            assert (orderList.get(0).getId().equals(order1.getId()));
        }
    }

    @Test
    void get() throws  Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order1 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,03,2,20)),"0777777777","TestAddress 1, 1111 TestOrt");
            Order order2 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,05,12,30)),"0888888888","TestAddress 2, 1111 TestOrt");
            order1.setPizzaOrders(getTestPizzaOrderSet(2,3));
            order2.setPizzaOrders(getTestPizzaOrderSet(4,3));

            orderDAO.create(order1);
            orderDAO.create(order2);

            assert(order1.getId()!=null);
            assert (orderDAO.get(1).getDateTime().equals(order1.getDateTime()));
            assert (orderDAO.get(2).getPhone().equals(order2.getPhone()));
            assert (orderDAO.get(99) == null);
        }
    }

    @Test
    void update() throws  Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order1 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,03,2,20)),"0777777777","TestAddress 1, 1111 TestOrt");
            Order order2 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,05,12,30)),"0888888888","TestAddress 2, 1111 TestOrt");
            order1.setPizzaOrders(getTestPizzaOrderSet(2,3));
            order2.setPizzaOrders(getTestPizzaOrderSet(4,3));

            orderDAO.create(order1);
            String newPhone = "0999999999";
            order1.setPhone(newPhone);
            orderDAO.update(order1);
            orderDAO.update(order2);
            assert (orderDAO.get(1).getPhone().equals(newPhone));
            assert (order2.getId() == null);
        }
    }

    @Test
    void save() throws  Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order1 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,03,2,20)),"0777777777","TestAddress 1, 1111 TestOrt");
            Order order2 = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,05,12,30)),"0888888888","TestAddress 2, 1111 TestOrt");
            order1.setPizzaOrders(getTestPizzaOrderSet(2,3));
            order2.setPizzaOrders(getTestPizzaOrderSet(4,3));

            orderDAO.create(order1);
            String newPhone = "0999999999";

            order1.setPhone(newPhone);
            orderDAO.save(order1);
            orderDAO.save(order2);
            assert (orderDAO.get(1).getPhone().equals(newPhone));
            assert (order2.getId() != null);
            assert (orderDAO.get(2).getDateTime().equals(order2.getDateTime()));
        }
    }

    @Test
    void delete() throws  Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order = new Order(Timestamp.valueOf(LocalDateTime.of(2023,06,03,2,20)),"0777777777","TestAddress 1, 1111 TestOrt");
            order.setPizzaOrders(getTestPizzaOrderSet(1,1));

            orderDAO.create(order);
            assert (order.getId() != null);
            assert (orderDAO.delete(1));
            assert (orderDAO.get(1) == null);
            assert (!orderDAO.delete(1));
        }
    }

    List<PizzaOrder> getTestPizzaOrderSet(int amount1, int amount2) throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            List<PizzaOrder> pizzaOrders = new ArrayList<>();
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1, pizza1, amount1);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1, pizza2, amount2);
            pizzaOrders.add(pizzaOrder1);
            pizzaOrders.add(pizzaOrder2);
            return pizzaOrders;
        }
    }
}