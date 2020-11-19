package softuni.jsonprocessing.domain.dto.export.query4;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartExportDtoQuery4 {

    @Expose
    private String name;
    @Expose
    private BigDecimal price;


    public PartExportDtoQuery4() {
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
