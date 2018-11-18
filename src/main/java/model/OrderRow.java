package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderRow {

    @Column(name = "item_name")
    private String itemName;

    @NotNull
    @Min(1)
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Min(1)
    @Column(name = "price")
    private Integer price;
}
