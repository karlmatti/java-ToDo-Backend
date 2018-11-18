package controller;

import dao.OrderDao;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @PostMapping("orders")
    @ResponseBody
    public Order save(@RequestBody @Valid Order order) {
        System.out.println("ORDER Save");
        orderDao.save(order);
        return order;
    }

    @GetMapping("orders/{orderId}")
    public Order getOrder(@PathVariable("orderId") Long orderId) {
        return orderDao.findOrderById(orderId);
    }

    @GetMapping("orders")
    public List<Order> getOrderList() {
        return orderDao.findAll();
    }

    @DeleteMapping("orders")
    public void deleteAll() {
        orderDao.deleteAll();
    }
}
