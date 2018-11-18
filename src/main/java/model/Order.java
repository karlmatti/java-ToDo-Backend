package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "order_number")
    private String orderNumber;

    @Valid
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "order_rows",
            joinColumns=@JoinColumn(name = "orders_id",
                    referencedColumnName = "id")
    )
    private List<OrderRow> orderRows = new ArrayList();

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void add(OrderRow or) {
        this.orderRows.add(or);
    }
}