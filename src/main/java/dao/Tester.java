package dao;

import conf.DbConfig;
import model.Order;
import model.OrderRow;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Tester {


    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
                new AnnotationConfigApplicationContext(DbConfig.class);

        OrderDao dao = ctx.getBean(OrderDao.class);
        OrderRowDao rowDao = ctx.getBean(OrderRowDao.class);


        OrderRow or1 = new OrderRow("MotherBoard", 5, 20);
        OrderRow or2 = new OrderRow("CPU", 2, 10);
        List<OrderRow> orderRowList = new ArrayList<>();
        orderRowList.add(or1);
        orderRowList.add(or2);
        /*
        Order savedOrder = orderDao.save(order);
        rowDao.save(savedOrder.getId(), order.getOrderRows());
        */
        Order order = dao.save(new Order(null,"order_number1", orderRowList));
        rowDao.save(order.getId(), orderRowList);
        System.out.println(order);
        System.out.println(dao.findById(1L));
        System.out.println(rowDao.findById(1L));
        //dao.delete(2L);

        //System.out.println(dao.findAll());

       // System.out.println(dao.findById(1L));

        ctx.close();
    }
}
