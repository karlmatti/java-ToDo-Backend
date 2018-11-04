package controller;

import dao.OrderDao;
import dao.OrderRowDao;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderRowDao rowDao;

    @PostMapping("orders")
    @ResponseBody
    public Order save(@RequestBody @Valid Order order) {
        Order savedOrder = orderDao.save(order);
        savedOrder.setOrderRows(rowDao.save(savedOrder.getId(), order.getOrderRows()));
        return savedOrder;
    }

    @GetMapping("orders/{orderId}")
    public Order getOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderDao.findById(orderId);
        order.setOrderRows(rowDao.findById(orderId));
        return order;
    }
}
