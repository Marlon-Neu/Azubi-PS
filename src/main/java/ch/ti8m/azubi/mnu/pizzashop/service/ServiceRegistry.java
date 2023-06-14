package ch.ti8m.azubi.mnu.pizzashop.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ServiceRegistry {

    private static ServiceRegistry instance;

    private final Map<Class<?>, Object> services = new HashMap<>();

    private ServiceRegistry()throws Exception {
        services.put(PizzaService.class, new PizzaServiceImplement());
        services.put(PizzaOrderService.class, new PizzaOrderServiceImplement());
        services.put(OrderService.class, new OrderServiceImplement());
    }

    public static synchronized ServiceRegistry getInstance() throws Exception{
        if (instance == null) {
            instance = new ServiceRegistry();
        }
        return instance;
    }

    public <S> S get(Class<S> serviceClass) {
        S service = serviceClass.cast(services.get(serviceClass));
        if (service == null) {
            throw new NoSuchElementException("Service not found: " + serviceClass.getName());
        }
        return service;
    }
}
