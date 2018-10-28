package servlet.dao;

import servlet.model.OrderRow;

import java.sql.*;
import java.util.List;

public class OrderRowDao {


    private static String URL = OrderDao.URL;


    public List<OrderRow> insertOrderRow(long orderId, List<OrderRow> orderRow)
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


    public void deleteOrderRowsById(long insertedId)
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

    public void deleteAllOrderRows()
    {
        String sql = "DELETE FROM order_row;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {
            stmt.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
