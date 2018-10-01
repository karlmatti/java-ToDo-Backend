package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/api/orders", "/orders/form"})
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<Order> orderList = new ArrayList();
    private long id = 0;
    private Order apiOrder;
    private Order formOrder = new Order();



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] stringArray;
        if(request.getServletPath().equalsIgnoreCase("/api/orders")) {

            ObjectMapper mapper = new ObjectMapper();
            String string = Util.asString(request.getInputStream());

            apiOrder = mapper.readValue(string, Order.class);
            id++;
            apiOrder.setId(id);
            orderList.add(apiOrder);

            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(apiOrder);

        } else if(request.getServletPath().equalsIgnoreCase("/orders/form")){

            String string = Util.asString(request.getInputStream());
            stringArray = string.split("=");
            formOrder.setOrderNumber(stringArray[1]);
            id++;
            formOrder.setId(id);
            orderList.add(formOrder);

            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(formOrder.getId());

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long givenId = Long.parseLong(request.getParameter("id"));
        response.setHeader("Content-Type", "application/json");
        for (Order order: orderList) {
            if(order.getId() == givenId){
                response.getWriter().print(order);
                break;
            }
        }
    }

}
