package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Ingredient;
import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO implements DAO<Pizza> {

    private Connection connection;

    public PizzaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Pizza> list() throws Exception {
        List<Pizza> pizzaList = new ArrayList<Pizza>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT id,name,price from pizza")){

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Pizza pizza = new Pizza();
                int id = resultSet.getInt("id");
                pizza.setId(id);
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getBigDecimal("price"));
                getIngredients(id, pizza);
                pizzaList.add(pizza);
            }
        }
        return pizzaList;
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
                getIngredients(id, pizza);
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
        for(Ingredient ingredient: pizza.getIngredients()){
            createIngredient(ingredient);
            createPizzaIngredient(pizza.getId(),ingredient.getId());
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
            for(Ingredient ingredient: pizza.getIngredients()){
                createIngredient(ingredient);
                createPizzaIngredient(pizza.getId(),ingredient.getId());
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
        try (PreparedStatement statement = connection.prepareStatement("delete from pizza_orders where pizza_id=?")){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement("delete from pizza_ingredients where pizza_id=?")){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement("delete from pizza where id=?")){
            statement.setInt(1,id);
            if(statement.executeUpdate() > 0){
                return true;
            }
        }
        return false;
    }


    private void getIngredients(int id, Pizza pizza) throws SQLException {
        List<Ingredient> ingredientList =new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("select id,name\n" +
                "from pizza_ingredients pi2 \n" +
                "join ingredient i on pi2.ingredient_id = i.id \n" +
                "where pi2.pizza_id = ?;")) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredientList.add(ingredient);
            }
        }
        pizza.setIngredients(ingredientList);
    }

    private void createIngredient(Ingredient ingredient) throws Exception {
        int index = findIngredient(ingredient.getName());
        if(index == -1){
            try(PreparedStatement statement = connection.prepareStatement("insert into ingredient (name) values (?)", Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1,ingredient.getName());
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                int generatedId = generatedKeys.getInt(1);
                ingredient.setId(generatedId);
            }
        }
        else {
            ingredient.setId(index);
        }
    }

    private void createPizzaIngredient(Integer pizza_id,Integer ingredient_id) throws  Exception{
        if((ingredient_id != null)&&(pizza_id!= null)){
            try (PreparedStatement statement = connection.prepareStatement("insert into pizza_ingredients (pizza_id,ingredient_id) values (?,?)")){
                statement.setInt(1,pizza_id);
                statement.setInt(2,ingredient_id);
                statement.executeUpdate();
            }
        }
    }

    private Integer findIngredient(String name) throws Exception{
        try(PreparedStatement statement = connection.prepareStatement("SELECT id from ingredient where UPPER('name') = UPPER(?)")){
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id");
            }
        }
        return -1;
    }
}
