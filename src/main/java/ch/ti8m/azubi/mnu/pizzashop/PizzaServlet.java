package ch.ti8m.azubi.mnu.pizzashop;

import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.service.PizzaService;
import ch.ti8m.azubi.mnu.pizzashop.service.ServiceRegistry;
import freemarker.template.Template;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/pizza")
public class PizzaServlet extends HttpServlet {
    private Template template;
    private PizzaService pizzaService;

    @Override
    public void init() throws ServletException {
        try {
            pizzaService = ServiceRegistry.getInstance().get(PizzaService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        template = new FreemarkerConfig().loadTemplate("pizza.ftl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Pizza> pizzaList = pizzaService.list();
            PrintWriter writer = resp.getWriter();
            Map<String,Object> model = new HashMap<>();
            model.put("pizzaList",pizzaList);

            template.process(model,writer);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
