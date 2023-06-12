package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class PizzaDAO implements DAO<Pizza> {

    private Connection connection;

    public PizzaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Pizza> list() throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("SELECT id,name,price from pizza")){
            List<Pizza> pizzaList = new ArrayList<Pizza>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Pizza pizza = new Pizza();
                pizza.setId(resultSet.getInt("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getBigDecimal("price"));
                pizzaList.add(pizza);
            }
            return pizzaList;
        }
    }

    public Pizza get(int id) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("select id,name,price from pizza where id = ?")){
            Pizza pizza = new Pizza();
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                pizza.setId(resultSet.getInt("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getBigDecimal("price"));
                return pizza;
            }
            return null;
        }
    }

    public Pizza create(Pizza pizza) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("insert into pizza (name " +
                ", price) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pizza.getName());
            statement.setBigDecimal(2,pizza.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int generatedId = generatedKeys.getInt(1);
            pizza.setId(generatedId);
        }
        return pizza;
    }

    public void update(Pizza pizza) throws Exception {
        if(pizza.getId() != null) {
            try (PreparedStatement statement = connection.prepareStatement("update pizza set name = ?, price  = ?" +
                    "where id = ?")) {
                statement.setString(1, pizza.getName());
                statement.setBigDecimal(2, pizza.getPrice());;
                statement.setInt(3,pizza.getId());
                statement.executeUpdate();
            }
        }
    }

    public Pizza save(Pizza pizza) throws Exception {
        if (pizza.getId() == null){
            return create(pizza);
        }
        else {
            update(pizza);
        }
        return pizza;
    }

    public boolean delete(int id) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("delete from pizza where id=?")){
            statement.setInt(1,id);
            if(statement.executeUpdate() > 0){
                return true;
            }
        }
        return false;
    }
}
