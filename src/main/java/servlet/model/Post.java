package servlet.model;

public class Post {

    private Long id;
    private String orderNumber;

    public Post() {}

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

    @Override
    public String toString() {
        return "{ " +
                "\"id\": " + id +
                ", \"orderNumber\": \"" + orderNumber + '\"' +
                " }";
    }
}
