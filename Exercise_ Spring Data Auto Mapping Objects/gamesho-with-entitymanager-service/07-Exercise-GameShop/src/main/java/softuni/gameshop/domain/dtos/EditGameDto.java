package softuni.gameshop.domain.dtos;

import java.math.BigDecimal;

public class EditGameDto {
    private Long id;
    private BigDecimal price;
    private double size;

    public EditGameDto() {
    }

    public EditGameDto(Long id, BigDecimal price, double size) {
        this.id = id;
        this.price = price;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
