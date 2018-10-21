package servlet;

import servlet.model.OrderRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

class OrderRowDao {


    private static String URL = OrderDao.URL;


    List<OrderRow> insertOrderRow(long orderId, List<OrderRow> orderRow)
    {
        for (OrderRow anOrderRow : orderRow) {
            String sql = "INSERT INTO order_row (item_name, quantity, price, order_id)" +
                    "VALUES (?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, anOrderRow.getItemName());
                ps.setInt(2, anOrderRow.getQuantity());
                ps.setInt(3, anOrderRow.getPrice());
                ps.setLong(4, orderId);

                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return orderRow;
    }


    void deleteOrderRowsById(long insertedId)
    {
        String sql = "DELETE FROM order_row " +
                "WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, insertedId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
