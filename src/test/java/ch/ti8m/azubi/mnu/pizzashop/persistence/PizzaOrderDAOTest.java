package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;
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

class PizzaOrderDAOTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws Exception{
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

            try (PreparedStatement statement = conn.prepareStatement("insert into orders (date, phone,address) values (?,?,?)")) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(2023,06,07,11,20)));
                statement.setString(2,"0777777777");
                statement.setString(3,"Testaddress 2,8000 Zurich");
                statement.executeUpdate();
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(2023,06,07,17,10)));
                statement.setString(2,"0777777778");
                statement.setString(3,"Testaddress 5,8000 Zurich");
                statement.executeUpdate();
            }
        }
    }

    @Test
    void create() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(conn);
            PizzaDAO pizzaDAO =new PizzaDAO(conn);
            List<PizzaOrder> pizzaOrders = new ArrayList<>();
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1,pizza1,2);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1,pizza2,3);
            pizzaOrders.add(pizzaOrder1);
            pizzaOrders.add(pizzaOrder2);
            pizzaOrderDAO.create(pizzaOrders);
        }
    }

    @Test
    void list() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(conn);
            PizzaDAO pizzaDAO =new PizzaDAO(conn);
            List<PizzaOrder> pizzaOrders1 = new ArrayList<>();
            List<PizzaOrder> pizzaOrders2 = new ArrayList<>();
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1,pizza1,2);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1,pizza2,3);
            pizzaOrders1.add(pizzaOrder1);
            pizzaOrders1.add(pizzaOrder2);
            pizzaOrderDAO.create(pizzaOrders1);
            PizzaOrder pizzaOrder3 = new PizzaOrder(2,pizza1,4);
            PizzaOrder pizzaOrder4 = new PizzaOrder(2,pizza2,5);
            pizzaOrders2.add(pizzaOrder3);
            pizzaOrders2.add(pizzaOrder4);
            pizzaOrderDAO.create(pizzaOrders2);

            List<List<PizzaOrder>> pizzaOrdersList = pizzaOrderDAO.list();
            assert (pizzaOrdersList.get(0).get(0).getOrder_id().equals(pizzaOrders1.get(0).getOrder_id()));
            assert (pizzaOrdersList.get(1).get(0).getOrder_id().equals(pizzaOrders2.get(0).getOrder_id()));
            /* Console Log Test
            System.out.println(pizzaOrdersList.get(1).get(0).getOrder_id());
            System.out.println(pizzaOrdersList.get(1).get(0).getPizza().getName());
            System.out.println(pizzaOrdersList.get(1).get(0).getAmount());
            */
        }
    }

    @Test
    void get() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(conn);
            PizzaDAO pizzaDAO =new PizzaDAO(conn);
            List<PizzaOrder> pizzaOrders = new ArrayList<>();
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1,pizza1,2);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1,pizza2,3);
            pizzaOrders.add(pizzaOrder1);
            pizzaOrders.add(pizzaOrder2);
            pizzaOrderDAO.create(pizzaOrders);

            List<PizzaOrder> pizzaOrderTest = pizzaOrderDAO.get(1);
            assert (pizzaOrderTest.get(0).getOrder_id().equals(pizzaOrders.get(0).getOrder_id()));
            /* Console Log Test
            System.out.println(pizzaOrders.get(0).getOrder_id());
            System.out.println(pizzaOrders.get(0).getPizza().getName());
            System.out.println(pizzaOrders.get(0).getAmount());
            */
        }
    }

    @Test
    void update() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(conn);
            PizzaDAO pizzaDAO =new PizzaDAO(conn);
            List<PizzaOrder> pizzaOrders = new ArrayList<>();
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1,pizza1,2);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1,pizza2,3);
            pizzaOrders.add(pizzaOrder1);
            pizzaOrders.add(pizzaOrder2);
            pizzaOrderDAO.create(pizzaOrders);
            int newAmount = 5;
            pizzaOrders.get(0).setAmount(newAmount);
            pizzaOrderDAO.update(pizzaOrders);
            List<PizzaOrder> pizzaOrdersTest = pizzaOrderDAO.get(1);
            assert (pizzaOrdersTest.get(0).getOrder_id().equals(pizzaOrders.get(0).getOrder_id()));
            assert (pizzaOrdersTest.get(0).getAmount().equals(newAmount));
        }
    }

    @Test
    void save() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(conn);
            PizzaDAO pizzaDAO =new PizzaDAO(conn);
            List<PizzaOrder> pizzaOrders = new ArrayList<>();
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1,pizza1,2);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1,pizza2,3);
            pizzaOrders.add(pizzaOrder1);
            pizzaOrders.add(pizzaOrder2);
            pizzaOrderDAO.save(pizzaOrders);
            int newAmount = 5;
            pizzaOrders.get(0).setAmount(newAmount);
            pizzaOrderDAO.save(pizzaOrders);
            List<PizzaOrder> pizzaOrdersTest = pizzaOrderDAO.get(1);
            assert (pizzaOrdersTest.get(0).getOrder_id().equals(pizzaOrders.get(0).getOrder_id()));
            assert (pizzaOrdersTest.get(0).getAmount().equals(newAmount));
        }
    }

    @Test
    void delete() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaOrderDAO pizzaOrderDAO = new PizzaOrderDAO(conn);
            PizzaDAO pizzaDAO =new PizzaDAO(conn);
            List<PizzaOrder> pizzaOrders = new ArrayList<>();
            Pizza pizza1 = pizzaDAO.get(1);
            Pizza pizza2 = pizzaDAO.get(2);
            PizzaOrder pizzaOrder1 = new PizzaOrder(1,pizza1,2);
            PizzaOrder pizzaOrder2 = new PizzaOrder(1,pizza2,3);
            pizzaOrders.add(pizzaOrder1);
            pizzaOrders.add(pizzaOrder2);
            pizzaOrderDAO.create(pizzaOrders);
            assert (pizzaOrderDAO.delete(1));
            assert (!pizzaOrderDAO.delete(1));
        }
    }
}