package servlet.model;
//https://bitbucket.org/mkalmo/hwtests/src/master/src/main/java/tests/model/Order.java

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private long id;
    private String orderNumber;
    private List<OrderRow> orderRows = new ArrayList();

    public Order() { }

    public Order(String orderNumber) { this.orderNumber = orderNumber; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderRow> getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(List<OrderRow> orderRow) {
        this.orderRows = orderRow;
    }

    @Override
    public String toString() {
        return "{ " +
                "\"id\": " + id +
                ", \"orderNumber\": \"" + orderNumber +
                "\", \"orderRows\": " + orderRows +
                " } ";
    }
}