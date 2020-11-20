package softuni.jsonprocessing.model.dto.export.query4;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductsExportDtoQuery4 {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductsExportDtoQuery4() {
    }

    public ProductsExportDtoQuery4(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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
}
