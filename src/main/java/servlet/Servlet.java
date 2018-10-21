package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.model.Order;
import servlet.model.OrderRow;
import util.InputAsString;

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
    private Order apiOrder;
    private Order formOrder = new Order();


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String[] stringArray;

        if(request.getServletPath().equalsIgnoreCase("/api/orders"))
        {
            ObjectMapper mapper = new ObjectMapper();

            String string = InputAsString.asString(request.getInputStream());

            apiOrder = mapper.readValue(string, Order.class);

            Order order = new OrderDao().insertOrder(apiOrder);

            List<OrderRow> orderRows = new OrderRowDao().insertOrderRow(order.getId(), apiOrder.getOrderRows());

            order.setOrderRows(orderRows);

            response.setHeader("Content-Type", "application/json");

            response.getWriter().print(order);

        } else if(request.getServletPath().equalsIgnoreCase("/orders/form"))
        {
            String string = InputAsString.asString(request.getInputStream());

            stringArray = string.split("=");

            formOrder.setOrderNumber(stringArray[1]);

            Order order = new OrderDao().insertOrder(formOrder);

            response.setHeader("Content-Type", "application/json");

            response.getWriter().print(order.getId());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Content-Type", "application/json");

        if(request.getParameter("id") == null)
        {
            List<Order> orderList = new OrderDao().getOrderList();
            response.getWriter().print(orderList);

        } else {
            Long givenId = Long.parseLong(request.getParameter("id"));
            Order order = new OrderDao().getOrdersById(givenId);
            response.getWriter().print(order);

        }
    }


    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        long givenId = Long.parseLong(request.getParameter("id"));

        new OrderRowDao().deleteOrderRowsById(givenId);
        new OrderDao().deleteOrdersById(givenId);
    }
}
