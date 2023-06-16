package ch.ti8m.azubi.mnu.pizzashop.freemarker;

import ch.ti8m.azubi.mnu.pizzashop.dto.Order;
import ch.ti8m.azubi.mnu.pizzashop.dto.Pizza;
import ch.ti8m.azubi.mnu.pizzashop.persistence.OrderDAO;
import ch.ti8m.azubi.mnu.pizzashop.persistence.PizzaDAO;
import ch.ti8m.azubi.mnu.pizzashop.service.MDBConnectionFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FreemarkerTest {

    private Configuration configuration(){
        Configuration config = new Configuration();
        config.setDefaultEncoding("UTF-8");
        config.setLocale(Locale.getDefault());
        config.setClassForTemplateLoading(getClass(),"/templates");
        config.setIncompatibleImprovements(new Version(2,3,20));
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return config;
    }

    @Test
    public void pizzaTest() throws Exception{
        Template template = configuration().getTemplate("pizza_test.ftl");
        PizzaDAO pizzaDAO = new PizzaDAO(MDBConnectionFactory.mariaDBConnection());

        List<Pizza> pizzaList = pizzaDAO.list();
        Map<String,Object> model = new HashMap<>();
        model.put("pizzaList",pizzaList);

        StringWriter writer = new StringWriter();
        template.process(model,writer);

        System.out.println(writer.toString());
    }

    @Test
    public void orderTest() throws Exception{
        Template template = configuration().getTemplate("order_test.ftl");
        OrderDAO orderDAO = new OrderDAO(MDBConnectionFactory.mariaDBConnection());
        Order order = orderDAO.get(3);


        StringWriter writer = new StringWriter();
        template.process(order,writer);

        System.out.println(writer.toString());
    }
}
