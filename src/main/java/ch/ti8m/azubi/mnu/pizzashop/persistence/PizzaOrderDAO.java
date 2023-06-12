package ch.ti8m.azubi.mnu.pizzashop.persistence;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PizzaOrderDAO implements DAO<List<PizzaOrder>> {

    private final Connection connection;

    public PizzaOrderDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<List<PizzaOrder>> list() throws Exception {
        List<List<PizzaOrder>> pizzaOrdersListList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select max(order_id) as id_max from pizza_orders")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id_max = resultSet.getInt("id_max");
                for (int i = 0; i < id_max; i++) {
                    pizzaOrdersListList.add(new ArrayList<>());
                }
            }
        }

        String sqlStatement = "select po.order_id,p.id,p.name,p.price,po.amount\n" +
                "from pizza p\n" +
                "inner join pizza_orders po  on p.id = po.pizza_id \n" +
                "order by po.order_id ; ";
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                PizzaOrder pizzaOrder = new PizzaOrder();
                Pizza pizza = new Pizza();
                pizza.setId(resultSet.getInt("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getBigDecimal("price"));
                int order_id = resultSet.getInt("order_id");
                pizzaOrder.setOrder_id(order_id);
                pizzaOrder.setPizza(pizza);
                pizzaOrder.setAmount(resultSet.getInt("amount"));
                pizzaOrdersListList.get(order_id-1).add(pizzaOrder);
            }
            return pizzaOrdersListList;
        }
    }

    @Override
    public List<PizzaOrder> get(int id) throws Exception {
        String sqlStatement = "select po.order_id,p.id,p.name,p.price,po.amount\n" +
                "from pizza p\n" +
                "inner join pizza_orders po  on p.id = po.pizza_id \n" +
                "where po.order_id = ?; ";
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)){
            List<PizzaOrder> pizzaOrdersList = new ArrayList<>();
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                PizzaOrder pizzaOrder = new PizzaOrder();
                Pizza pizza = new Pizza();
                pizza.setId(resultSet.getInt("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getBigDecimal("price"));
                pizzaOrder.setOrder_id(resultSet.getInt("order_id"));
                pizzaOrder.setPizza(pizza);
                pizzaOrder.setAmount(resultSet.getInt("amount"));
                pizzaOrdersList.add(pizzaOrder);
            }
            return pizzaOrdersList;
        }
    }


    @Override
    public List<PizzaOrder> create(List<PizzaOrder> pizzaOrderList) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("insert into pizza_orders (order_id,pizza_id,amount)\n "+
                "values ( ?,?,? )")) {
            for (PizzaOrder pizzaOrder : pizzaOrderList){
                statement.setInt(1,pizzaOrder.getOrder_id());
                statement.setInt(2,pizzaOrder.getPizza().getId());
                statement.setInt(3,pizzaOrder.getAmount());
                statement.executeUpdate();
            }
        }
        return pizzaOrderList;
    }

    @Override
    public void update(List<PizzaOrder> pizzaOrderList) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("update pizza_orders set amount = ? where order_id = ? and pizza_id = ?")){
            for(PizzaOrder pizzaOrder: pizzaOrderList){
                statement.setInt(1,pizzaOrder.getAmount());
                statement.setInt(2,pizzaOrder.getOrder_id());
                statement.setInt(3,pizzaOrder.getPizza().getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public List<PizzaOrder> save(List<PizzaOrder> pizzaOrderList) throws Exception {
        String sqlStatement = "select case " +
                "when exists(select order_id from pizza_orders where order_id = ?) \n" +
                "and exists(select pizza_id from pizza_orders where pizza_id = ?) \n" +
                "then true else false end as ifExists";
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            boolean exists = false;
            for(PizzaOrder pizzaOrder:pizzaOrderList){
                statement.setInt(1,pizzaOrder.getOrder_id());
                statement.setInt(2,pizzaOrder.getPizza().getId());
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    exists = resultSet.getBoolean("ifExists");
                    if(exists){ break;}
                }
            }
            if(exists){
                update(pizzaOrderList);
            }
            else {
                    create(pizzaOrderList);
            }
        }
        return pizzaOrderList;
    }

    @Override
    public boolean delete(int id) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("delete from pizza_orders where order_id=?")){
            statement.setInt(1,id);
            if(statement.executeUpdate() > 0){
                return true;
            }
        }
        return false;
    }
}
