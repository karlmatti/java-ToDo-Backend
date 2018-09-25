package servlet;

import servlet.model.Post;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/api/orders")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static long id = 0;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String string = Util.asString(req.getInputStream());

        string = string.replace("{", "");
        string = string.replace("}", "");
        string = string.replace("\"", "");
        string = string.trim();
        String[] couples;
        String[] postedOrderNr;
        Post post = new Post();
        post.setId(id);
        if(string.contains(",")){
            couples = string.split(",");

            System.out.println(Arrays.asList(couples));
            if(couples[0].toLowerCase().contains("id")){
                postedOrderNr = couples[1].split(":");
                postedOrderNr[1] = postedOrderNr[1].trim();
                post.setOrderNumber(postedOrderNr[1]);
            } else {
                postedOrderNr = couples[0].split(":");
                postedOrderNr[0] = postedOrderNr[0].trim();
                post.setOrderNumber(postedOrderNr[0]);
            }

        } else {
            postedOrderNr = string.split(":");
            postedOrderNr[1] = postedOrderNr[1].trim();
            post.setOrderNumber(postedOrderNr[1]);
        }

        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(post);
        id++;

    }


}
