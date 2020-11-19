package softuni.jsonprocessing.domain.dto.importDto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartDtoImport {
    //{ "name":"Bonnet/hood", "price":1001.34, "quantity":10},

    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private Integer quantity;


    public PartDtoImport() {
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
