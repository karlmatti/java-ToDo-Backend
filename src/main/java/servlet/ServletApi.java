package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.dao.OrderDao;
import servlet.dao.OrderRowDao;
import servlet.model.Order;
import servlet.model.OrderRow;
import servlet.model.ValidationError;
import servlet.model.ValidationErrors;
import util.InputAsString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/api/orders"})
public class ServletApi extends HttpServlet {

    private Order apiOrder;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        String string = InputAsString.asString(request.getInputStream());

        if(getValidationErrors(string).getErrors().get(0).getCode() != null){
            ValidationErrors errors = getValidationErrors(string);
            String json2 = mapper.writeValueAsString(errors);
            response.setHeader("Content-Type", "application/json");
            response.setStatus(400);
            response.getWriter().print(json2);

        } else {

            apiOrder = mapper.readValue(string, Order.class);
            Order order = new OrderDao().insertOrder(apiOrder);
            List<OrderRow> orderRows = new OrderRowDao().insertOrderRow(order.getId(), apiOrder.getOrderRows());
            order.setOrderRows(orderRows);
            String json = new ObjectMapper().writeValueAsString(order);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(json);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Content-Type", "application/json");
        if(request.getServletPath().equalsIgnoreCase("/api/orders")) {

            if (request.getParameter("id") == null) {
                String json = new ObjectMapper().writeValueAsString(
                        new OrderDao().getOrderList());
                response.getWriter().print(json);

            } else  {
                Long givenId = Long.parseLong(request.getParameter("id"));
                String json = new ObjectMapper().writeValueAsString(
                        new OrderDao().getOrdersById(givenId));
                response.getWriter().print(json);
            }

        }
    }


    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        if(request.getServletPath().equalsIgnoreCase("/api/orders"))
        {
            if (request.getParameter("id") == null)
            {

                new OrderRowDao().deleteAllOrderRows();
                new OrderDao().deleteAllOrders();

            } else {

                long givenId = Long.parseLong(request.getParameter("id"));
                new OrderRowDao().deleteOrderRowsById(givenId);
                new OrderDao().deleteOrdersById(givenId);

            }
        }
    }

    private ValidationErrors getValidationErrors(String request) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Order order = mapper.readValue(request, Order.class);


        ValidationError error = new ValidationError();

        ValidationErrors errors = new ValidationErrors();

        List<String> arguments = new ArrayList<>();

        List<ValidationError> errorList = new ArrayList<>();

        if(order.getOrderNumber().length() < 2){
            error.setCode("too_short_number");
            arguments.add(order.getOrderNumber());
            error.setArguments(arguments);

            errorList.add(error);

            errors.setErrors(errorList);

            return errors;
        } else {
            errorList.add(error);
            errors.setErrors(errorList);
            return errors;
        }


    }
}
