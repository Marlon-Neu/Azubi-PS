package ch.ti8m.azubi.mnu.pizzashop.ws;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.service.OrderService;
import ch.ti8m.azubi.mnu.pizzashop.service.ServiceRegistry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/order")
public class OrderEndpoint {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> listOrder() throws Exception{
        return orderService().list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("id")int id) throws Exception{
        return orderService().get(id);
    }


    private static OrderService orderService() throws Exception
    {
        return ServiceRegistry.getInstance().get(OrderService.class);
    }

}
