package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Ingredient;
import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


class PizzaDAOTest {

    @BeforeEach
    void setUp() throws Exception{
        try (Connection conn = ConnectionFactory.testConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists pizza_orders");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists pizza_ingredients");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists orders");
            }

            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table if exists ingredient");
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
                statement.execute("CREATE TABLE ingredient (\n" +
                        "  id int NOT NULL AUTO_INCREMENT,\n" +
                        "  name varchar(20) NOT NULL,\n" +
                        "  PRIMARY KEY (id)\n" +
                        ")");
            }
            try (Statement statement = conn.createStatement()) {
                statement.execute("CREATE TABLE pizza_ingredients (\n" +
                        "  pizza_id int not NULL,\n" +
                        "  ingredient_id int not NULL,\n" +
                        "  foreign key (ingredient_id) references ingredient (id),\n" +
                        "  foreign key (pizza_id) references pizza (id)" +
                        ")");
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
        }
    }


    @Test
    void create() throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()) {
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza = new Pizza("Margherita",new BigDecimal("17.23"));
            Ingredient ingredient = new Ingredient("Tomato");
            List<Ingredient> ingredientList = new ArrayList<Ingredient>() {{add(ingredient);}};
            pizza.setIngredients(ingredientList);
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
            List<Ingredient> ingredientList = new ArrayList<Ingredient>() {{add(new Ingredient("Tomato"));
                add (new Ingredient("Cheese"));}};
            pizza1.setIngredients(ingredientList);
            pizza2.setIngredients(ingredientList);
            pizza3.setIngredients(ingredientList);
            pizzaDAO.create(pizza1);
            pizzaDAO.create(pizza2);
            pizzaDAO.create(pizza3);
            assert (pizza1.getId() != null);
            List<Pizza> pizzaList = pizzaDAO.list();
            assert (pizzaList.get(0).getName().equals(pizza1.getName()));
            assert (pizzaList.get(1).getName().equals(pizza2.getName()));
            assert (pizzaList.get(2).getName().equals(pizza3.getName()));
            assert (pizzaList.get(0).getIngredients().get(0).getName().equals("Tomato"));
            assert (pizzaList.get(1).getIngredients().get(1).getName().equals("Cheese"));
        }
    }

    @Test
    void get()throws Exception{
        try(Connection conn = ConnectionFactory.testConnection()){
            PizzaDAO pizzaDAO = new PizzaDAO(conn);
            Pizza pizza1 = new Pizza("Margherita",new BigDecimal("17.23"));
            Pizza pizza2 = new Pizza("Hawaiian",new BigDecimal("18.99"));
            List<Ingredient> ingredientList = new ArrayList<Ingredient>() {{add(new Ingredient("Tomato"));
                add (new Ingredient("Cheese"));}};
            pizza1.setIngredients(ingredientList);
            pizza2.setIngredients(ingredientList);
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
            List<Ingredient> ingredientList = new ArrayList<Ingredient>() {{add(new Ingredient("Tomato"));
                add (new Ingredient("Cheese"));}};
            pizza1.setIngredients(ingredientList);
            pizza2.setIngredients(ingredientList);

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
            List<Ingredient> ingredientList = new ArrayList<Ingredient>() {{add(new Ingredient("Tomato"));
                add (new Ingredient("Cheese"));}};
            pizza1.setIngredients(ingredientList);
            pizza2.setIngredients(ingredientList);

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
            List<Ingredient> ingredientList = new ArrayList<Ingredient>() {{add(new Ingredient("Tomato"));
                add (new Ingredient("Cheese"));}};
            pizza.setIngredients(ingredientList);
            pizzaDAO.create(pizza);
            assert(pizza.getId() != null);
            assert (pizzaDAO.delete(1));
            assert (pizzaDAO.get(1) == null);
            assert (!pizzaDAO.delete(1));
        }
    }
}