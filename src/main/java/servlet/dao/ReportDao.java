package servlet.dao;

import servlet.model.Report;

import java.sql.*;

public class ReportDao
{
    private static String URL = OrderDao.URL;

    private Integer getOrderCount()
    {
        String sql = "SELECT COUNT(id) AS count FROM orders;";
        int count;
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {

            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            count = rs.getInt("count");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return count;
    }

    private Integer getAverageOrderAmount()
    {

        String sql = "SELECT AVG(quantitySum) AS average " +
                "FROM (" +
                "SELECT order_id, SUM(quantity * price) AS quantitySum " +
                "FROM order_row " +
                "GROUP BY order_id) orderRow;";
        int average;
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {

            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            average = rs.getInt("average");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return average;
    }

    private Integer getTurnoverWithoutVAT()
    {
        String sql = "SELECT SUM(priceSum) AS priceSum " +
                "FROM (" +
                "SELECT order_id, SUM(quantity * price) AS priceSum " +
                "FROM order_row " +
                "GROUP BY order_id) orderRow;";
        int turnover;
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement())
        {

            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            turnover = rs.getInt("priceSum");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return turnover;
    }

    public Report getReport()
    {
        Report report = new Report();

        //   - mitu tellimust on sisestatud;
        report.setCount(getOrderCount());

        //   - mis on tellimuse keskmine hind;
        report.setAverageOrderAmount(getAverageOrderAmount());

        //   - mis on tellimuste kogumaksumus;
        report.setTurnoverWithoutVAT(getTurnoverWithoutVAT());

        //   - kui palju käibemaks sellelt summalt (20%);
        int vat = (int) (getTurnoverWithoutVAT() * 0.2);
        report.setTurnoverVAT(vat);

        //   - kui palju on tellimuste summa koos käibemaksuga.
        report.setTurnoverWithVAT(report.getTurnoverWithoutVAT() + vat);

        return report;
    }

}
