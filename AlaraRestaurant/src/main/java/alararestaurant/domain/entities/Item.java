package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item  extends BaseEntity{

    //
    //name – text with min length 3 and max length 30 (required, unique)
    //
    //category – the item’s category (required)
    //
    //price – decimal (non-negative, minimum value: 0.01, required)
    //
    //orderItems – collection of type OrderItem

    private String name;
    private Category category;
    private BigDecimal price;
    private Set<OrderItem> orderItems;

    public Item() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(nullable = false)
    @DecimalMin(value = "0.01",inclusive = true)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToMany(mappedBy = "item")
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
