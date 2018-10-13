package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.model.Order;
import util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/orders", "/orders/form"})
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private long id = 0;
    private Order apiOrder;
    private Order formOrder = new Order();



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] stringArray;
        if(request.getServletPath().equalsIgnoreCase("/api/orders")) {

            ObjectMapper mapper = new ObjectMapper();
            String string = InputAsString.asString(request.getInputStream());

            apiOrder = mapper.readValue(string, Order.class);

            Order order = new OrderDao().insertOrder(apiOrder);

            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(order);

        } else if(request.getServletPath().equalsIgnoreCase("/orders/form")){

            String string = InputAsString.asString(request.getInputStream());
            stringArray = string.split("=");
            formOrder.setOrderNumber(stringArray[1]);

            Order order = new OrderDao().insertOrder(formOrder);

            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(order.getId());

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Order> orderList = new OrderDao().getOrderList();

        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(orderList);

    }

}
