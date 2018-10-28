package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.dao.ReportDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/orders/report"})
public class ServletReport extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Content-Type", "application/json");

        String json = new ObjectMapper().writeValueAsString(
                new ReportDao().getReport());

        response.getWriter().print(json);

    }
}
