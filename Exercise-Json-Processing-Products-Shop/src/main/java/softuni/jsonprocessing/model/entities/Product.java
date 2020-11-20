package softuni.jsonprocessing.model.entities;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Set<Category> categories;

    private User buyer;
    private User seller;
    public Product() {
    }

    @Column(name = "name")
    @Length(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @ManyToMany(mappedBy = "products",fetch = FetchType.EAGER,targetEntity = Category.class)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> products) {
        this.categories = products;
    }

    @OneToOne
    @JoinColumn(name = "buyer_id")
    public User getBuyer() {
        return buyer;
    }
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
    @OneToOne
    @JoinColumn(name = "seller_id",nullable = false)
    public User getSeller() {
        return seller;
    }
    public void setSeller(User seller) {
        this.seller = seller;
    }



}
