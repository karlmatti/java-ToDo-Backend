package servlet;

import util.FileUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class Listener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Servlet is starting up!");

        try (Connection conn = DriverManager.getConnection(OrderDao.URL);
             Statement stmt = conn.createStatement())
        {
            stmt.executeUpdate(FileUtil.readFileFromClasspath("schema.sql"));
            System.out.println("Schema.sql statements have been executed.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Servlet is shutting down!");
    }
}
