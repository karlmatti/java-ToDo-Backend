package dao;

import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class OrderDao {

    @Autowired
    private JdbcTemplate template;

    public Order save(Order order) {
        String sql = "INSERT INTO orders (id, order_number) " +
                "VALUES (NEXT VALUE FOR seq1, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"});

            ps.setString(1, order.getOrderNumber());

            return ps;
            }, holder);

        order.setId(holder.getKey().longValue());

        return order;
    }

    public Order findById(Long id) {
        String sql = "SELECT id, order_number " +
                "FROM orders WHERE id = ?";

        return template.queryForObject(sql, new Object[] {id}, getOrderRowMapper());
    }

    private RowMapper<Order> getOrderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getLong("id"),
                rs.getString("order_number"),
                null);

    }

    /*public Order findById(Long id) {
        String sql = "SELECT orders.id, " +
                "orders.order_number, order_row.item_name, " +
                "order_row.quantity, order_row.price " +
                "FROM  orders " +
                "LEFT JOIN order_row ON orders.id = order_row.order_id " +
                "WHERE orders.id = ?";

        return template.queryForObject(sql, new Object[] {id}, getOrderRowMapper());
    }

    private RowMapper<Order> getOrderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getLong("id"),
                rs.getString("order_number"));
                rs.
    }
*/
}
