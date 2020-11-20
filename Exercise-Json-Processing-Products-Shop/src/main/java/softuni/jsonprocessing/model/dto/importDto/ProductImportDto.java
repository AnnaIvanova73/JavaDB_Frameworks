package softuni.jsonprocessing.model.dto.importDto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.jsonprocessing.model.entities.User;

import java.math.BigDecimal;

public class ProductImportDto {

    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    private User buyer;

    private User seller;

    public ProductImportDto() {
    }

    @Length(min = 3, message = "User last name should be at least 3 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
