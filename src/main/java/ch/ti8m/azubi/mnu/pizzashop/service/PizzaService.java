package ch.ti8m.azubi.mnu.pizzashop.service;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;

import java.util.List;
import java.util.NoSuchElementException;

public interface PizzaService {

    Pizza get(int id) throws NoSuchElementException;
    List<Pizza> list() throws  Exception;
    Pizza create(Pizza pizza) throws  IllegalArgumentException;
    void update(Pizza pizza) throws IllegalArgumentException;
    void remove(int id) throws NoSuchElementException;

}
