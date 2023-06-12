package ch.ti8m.azubi.mnu.pizzashop.persistence;

import java.util.List;

public interface DAO<E>{
    List<E> list() throws Exception;
    E get(int id) throws Exception;
    E create(E entity) throws Exception;
    void update(E entity) throws Exception;
    E save(E entity) throws Exception;
    boolean delete(int id) throws Exception;
}
