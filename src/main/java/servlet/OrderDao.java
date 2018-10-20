package servlet;

import servlet.model.Order;
import servlet.model.OrderRow;

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
            List<OrderRow> orderRows = OrderRowDao.insertOrderRow(rs.getLong("id"), order);
            return new Order(rs.getLong("id"), order.getOrderNumber(), orderRows);
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

    public Order getOrdersById(long insertedId){

        Order o = new Order();
        List<OrderRow> orList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery("SELECT orders.id, " +
                    "orders.order_number, order_row.item_name, " +
                    "order_row.quantity, order_row.price " +
                    "FROM orders " +
                    "LEFT JOIN order_row ON orders.id = order_row.order_id");


            boolean flag = false;
            while (rs.next())
            {
                OrderRow or = new OrderRow(
                        rs.getString("item_name"),
                        rs.getInt("quantity"),
                        rs.getInt("price")
                        );
                System.out.println(or);
                orList.add(or);
                if(flag == false){
                    o = new Order(
                            rs.getLong("id"),
                            rs.getString("order_number")
                            );
                    System.out.println(o);
                    flag = true;
                }



            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        o.setOrderRows(orList);
        System.out.println("return"+o);
        return o;

    }

}
