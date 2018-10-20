package servlet;

import servlet.model.Order;
import servlet.model.OrderRow;

import java.sql.*;
import java.util.List;

public class OrderRowDao {
    public static String URL = OrderDao.URL;

    public static List<OrderRow> insertOrderRow(long orderId, Order order) {
        List<OrderRow> orderRows = order.getOrderRows();
        for (int i = 0; i < orderRows.size(); i++) {
            String sql = "INSERT INTO order_row (item_name, quantity, price, order_id)" +
                    "VALUES (?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, orderRows.get(i).getItemName());
                ps.setInt(2, orderRows.get(i).getQuantity());
                ps.setInt(3, orderRows.get(i).getPrice());
                ps.setLong(4, orderId);

                ps.executeUpdate();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return orderRows;
    }
}
