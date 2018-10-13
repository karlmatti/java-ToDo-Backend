package servlet;

import servlet.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public static String URL = "jdbc:hsqldb:mem:my-database";

    public Order insertOrder(Order order) {

        String sql = "INSERT INTO orders (id, order_number)" +
                "VALUES (NEXT VALUE FOR seq1, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {
            ps.setString(1, order.getOrderNumber());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            rs.next();

            return new Order(rs.getLong("id"), order.getOrderNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Order> getOrderList()
    {
        List<Order> orderList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

            while (rs.next())
            {
                Order o = new Order(
                        rs.getLong("id"),
                        rs.getString("order_number"));
                orderList.add(o);
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return orderList;
    }
}
