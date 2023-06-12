package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;


class PizzaDAOTest {

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
        }
    }


    @Test
    void create() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza = new Pizza("Margherita",new BigDecimal("17.23"));
            pizzaDAO.create(pizza);
            assert(pizza.getId() != null);
            assert(pizza.getId() == 1);
        }
    }

    @Test
    void list() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza1 = new Pizza("Margherita",new BigDecimal("17.23"));
            Pizza pizza2 = new Pizza("Hawaiian",new BigDecimal("18.99"));
            Pizza pizza3 = new Pizza("Marlonian",new BigDecimal("19.99"));
            pizzaDAO.create(pizza1);
            pizzaDAO.create(pizza2);
            pizzaDAO.create(pizza3);
            assert (pizza1.getId() != null);
            List<Pizza> pizzaList = pizzaDAO.list();
            assert (pizzaList.get(0).getName().equals(pizza1.getName()));
            assert (pizzaList.get(1).getName().equals(pizza2.getName()));
            assert (pizzaList.get(2).getName().equals(pizza3.getName()));
        }
    }

    @Test
    void get()throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza1 = new Pizza("Margherita",new BigDecimal("17.23"));
            Pizza pizza2 = new Pizza("Hawaiian",new BigDecimal("18.99"));
            pizzaDAO.create(pizza1);
            pizzaDAO.create(pizza2);
            assert (pizza1.getId() != null);
            assert (pizzaDAO.get(1).getName().equals(pizza1.getName()));
            assert (pizzaDAO.get(2).getName().equals(pizza2.getName()));
            assert (pizzaDAO.get(99) == null);
        }
    }


    @Test
    void update() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza1 = new Pizza("Margherita",new BigDecimal("17.23"));
            Pizza pizza2 = new Pizza("Hawaiian",new BigDecimal("18.99"));
            pizzaDAO.create(pizza1);
            BigDecimal newPrice = new BigDecimal("15.10");
            pizza1.setPrice(newPrice);
            pizzaDAO.update(pizza1);
            pizzaDAO.update(pizza2);
            assert (pizzaDAO.get(1).getPrice().equals(newPrice));
            assert (pizza2.getId() == null);
        }
    }

    @Test
    void save() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza1 = new Pizza("Margherita",new BigDecimal("17.23"));
            Pizza pizza2 = new Pizza("Hawaiian",new BigDecimal("18.99"));
            pizzaDAO.create(pizza1);
            BigDecimal newPrice = new BigDecimal("15.10");
            pizza1.setPrice(newPrice);
            pizzaDAO.save(pizza1);
            pizzaDAO.save(pizza2);
            assert (pizzaDAO.get(1).getPrice().equals(newPrice));
            assert (pizza2.getId() != null);
            assert (pizzaDAO.get(2).getName().equals(pizza2.getName()));
        }
    }

    @Test
    void delete() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza = new Pizza("Margherita",new BigDecimal("17.23"));
            pizzaDAO.create(pizza);
            assert(pizza.getId() != null);
            assert (pizzaDAO.delete(1));
            assert (pizzaDAO.get(1) == null);
            assert (!pizzaDAO.delete(1));
        }
    }
}