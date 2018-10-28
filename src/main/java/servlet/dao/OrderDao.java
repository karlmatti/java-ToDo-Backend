package servlet.dao;

import servlet.model.Order;
import servlet.model.OrderRow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao
{


    public static String URL = "jdbc:hsqldb:mem:my-database";


    public Order insertOrder(Order order)
    {

        String sql = "INSERT INTO orders (id, order_number)" +
                "VALUES (NEXT VALUE FOR seq1, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"}))
        {
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

        String sql = "SELECT orders.id, " +
                "orders.order_number, order_row.item_name, " +
                "order_row.quantity, order_row.price " +
                "FROM  orders " +
                "LEFT JOIN order_row ON orders.id = order_row.order_id " +
                "ORDER BY orders.id";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery(sql);

            long lastId = 0;

            while (rs.next())
            {

                OrderRow or = new OrderRow(
                        rs.getString("item_name"),
                        rs.getInt("quantity"),
                        rs.getInt("price")
                );

                if(lastId != rs.getLong("id"))
                {
                    Order o = new Order(
                            rs.getLong("id"),
                            rs.getString("order_number")
                    );

                    o.addOrderRows(or);
                    orderList.add(o);

                } else {
                    orderList.get(orderList.size()-1).addOrderRows(or);

                }

                lastId = rs.getLong("id");

            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return orderList;
   }


    public Order getOrdersById(long insertedId)
    {
        Order o = new Order();

        List<OrderRow> orList = new ArrayList<>();

        String sql = "SELECT orders.id, " +
                "orders.order_number, order_row.item_name, " +
                "order_row.quantity, order_row.price " +
                "FROM  orders " +
                "LEFT JOIN order_row ON orders.id = order_row.order_id " +
                "WHERE orders.id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, insertedId);

            ResultSet rs = ps.executeQuery();

            boolean flag = false;

            while (rs.next())
            {
                OrderRow or = new OrderRow(
                        rs.getString("item_name"),
                        rs.getInt("quantity"),
                        rs.getInt("price")
                        );

                orList.add(or);

                if(!flag)
                {
                    o = new Order(
                            rs.getLong("id"),
                            rs.getString("order_number")
                            );

                    flag = true;
                }
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        o.setOrderRows(orList);

        return o;
    }


    public void deleteOrdersById(long insertedId)
    {
        String sql = "DELETE FROM orders " +
                "WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, insertedId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllOrders()
    {
        String sql = "DELETE FROM orders;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {
            stmt.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
