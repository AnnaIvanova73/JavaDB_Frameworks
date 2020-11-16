package softuni.gameshop.domain.dtos.view;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DtoSingleGameView {

    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public DtoSingleGameView() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        String sb = "Title=: " + title +
                System.lineSeparator() +
                "Price: " + price + System.lineSeparator() +
                "Description: " + description + System.lineSeparator() +
                "ReleaseDate: " + releaseDate + System.lineSeparator();
        return sb.trim();
    }
}
