package dao;

import model.OrderRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class OrderRowDao {

    @Autowired
    private JdbcTemplate template;

    public List<OrderRow> save(Long id, List<OrderRow> orderRows) {
        String sql = "INSERT INTO order_row (item_name, quantity, price, order_id) " +
                "VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        for (OrderRow orderRow : orderRows) {
            template.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(1, orderRow.getItemName());
                    ps.setInt(2, orderRow.getQuantity());
                    ps.setInt(3, orderRow.getPrice());
                    ps.setLong(4, id);

                return ps;
            }, holder);
        }

        return orderRows;
    }



    public List<OrderRow> findById(Long id) {
        String sql = "SELECT item_name, quantity, price, order_id " +
                "FROM order_row WHERE order_id = ?";

        return template.query(sql, new Object[] {id}, getOrderRowRowMapper());
    }


    private RowMapper<OrderRow> getOrderRowRowMapper() {
        return (rs, rowNum) -> new OrderRow(
                rs.getString("item_name"),
                rs.getInt("quantity"),
                rs.getInt("price"));

    }

}
