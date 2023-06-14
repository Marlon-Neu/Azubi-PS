package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.persistence.PizzaDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class PizzaServiceImplement implements PizzaService{
    private PizzaDAO pizzaDAO;


    public PizzaServiceImplement() throws SQLException {
        Connection connection = MDBConnectionFactory.mariaDBConnection();
        pizzaDAO = new PizzaDAO(connection);
    }
    @Override
    public Pizza get(int id) throws Exception {
        Pizza pizza = pizzaDAO.get(id);
        if(pizza == null){
            throw new NoSuchElementException("No pizza with id "+id+" in the database." );
        }
        return pizza;
    }

    @Override
    public List<Pizza> list() throws Exception {
        return pizzaDAO.list();
    }

    @Override
    public Pizza create(Pizza pizza) throws Exception {
        if (pizza == null){
            throw new IllegalArgumentException("Pizza is required");
        }
        if (pizza.getId() != null){
            throw new IllegalArgumentException("New pizza must not have an ID");
        }
        pizzaDAO.create(pizza);
        return pizza;
    }
    @Override
     public void update(Pizza pizza) throws Exception {
        if (pizza == null){
            throw new IllegalArgumentException("Pizza is required");
        }
        if(pizza.getId()==null){
            throw new IllegalArgumentException("Pizza ID is required");
        }
        pizzaDAO.update(pizza);
    }
    @Override
    public Pizza save(Pizza pizza) throws Exception {
        if (pizza == null){
            throw new IllegalArgumentException("Pizza is required");
        }
        pizzaDAO.save(pizza);
        return pizza;
    }

    @Override
    public void remove(int id) throws Exception {
        pizzaDAO.delete(id);
    }
}
