package ch.ti8m.azubi.mnu.pizzashop;


import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.dto.PizzaOrder;
import ch.ti8m.azubi.mnu.pizzashop.service.OrderService;
import ch.ti8m.azubi.mnu.pizzashop.service.PizzaService;
import ch.ti8m.azubi.mnu.pizzashop.service.ServiceRegistry;
import freemarker.template.Template;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order/*")
public class OrderServlet extends HttpServlet {
    Template template;
    OrderService orderService;
    PizzaService pizzaService;

    @Override
    public void init() throws ServletException {
        try {
            orderService = ServiceRegistry.getInstance().get(OrderService.class);
            pizzaService = ServiceRegistry.getInstance().get(PizzaService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        template = new FreemarkerConfig().loadTemplate("order.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = new Order();
        try {
            order.setAddress(req.getParameter("address"));
            order.setPhone(req.getParameter("phone"));
            order.setDateTime(Timestamp.valueOf(LocalDateTime.now()));

            List<PizzaOrder> pizzaOrderList = new ArrayList<>();
            String[] pizzaIDs = req.getParameterValues("pizzaID[]");
            String[] pizzaAmounts = req.getParameterValues("pizzaAmount[]");
            for(int i = 0;i < pizzaIDs.length;i++){

                Integer amount = Integer.valueOf(pizzaAmounts[i]);
                Pizza pizza = pizzaService.get(Integer.parseInt(pizzaIDs[i]));
                pizzaOrderList.add(new PizzaOrder(pizza,amount));
            }
            order.setPizzaOrders(pizzaOrderList);
            orderService.create(order);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("order/"+order.getId());

    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String pathInfo = req.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            Integer id = Integer.valueOf(pathParts[1]);
            Order order = orderService.get(id);
            PrintWriter writer = resp.getWriter();
            template.process(order,writer);
        }catch (Exception e){throw new RuntimeException();}
    }
}
