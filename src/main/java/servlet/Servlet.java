package servlet;

import servlet.model.Post;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String[] map = string.split(":");
        map[1] = map[1].trim();
        Post post = new Post();
        post.setId(id);
        post.setOrderNumber(map[1]);
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(post);
        id++;

    }


}
