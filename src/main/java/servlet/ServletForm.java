package servlet;

import servlet.dao.OrderDao;
import servlet.model.Order;
import util.InputAsString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/orders/form"})
public class ServletForm extends HttpServlet {
    private Order formOrder = new Order();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String string = InputAsString.asString(request.getInputStream());

            String[] stringArray = string.split("=");

            formOrder.setOrderNumber(stringArray[1]);

            Order order = new OrderDao().insertOrder(formOrder);

            response.setHeader("Content-Type", "application/json");

            response.getWriter().print(order.getId());

    }
}
