package softuni.gameshop.domain.dtos.seed;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddGameDto {



    private String title;
    private BigDecimal price;
    private double size;
    private String trailer;
    private String thumbnail;
    private String description;
    private LocalDate releaseDate;

    public AddGameDto() {
    }


    public AddGameDto(String title,
                      BigDecimal price,
                      double size,
                      String trailer,
                      String thumbnail,
                      String description,
                      LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnail = thumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp = "^([A-Z])+(\\w)+$",message = "Incorrect title!Must begin with capital letter")
    @Length(min = 3,max = 100,message = "Incorrect title! Title should be BETWEEN 3 AND 100 symbols!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DecimalMin(value = "0", message = "Price should be positive number")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //@Min not for double
    @DecimalMin(value = "0" , message = "Size should be positive number")
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Length(min = 11,max = 11,message = "Incorrect trailer! Should be exactly 11 symbols")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "^((https?):\\/\\/)?.*", message = "Incorrect thumbnail!")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Length(min = 11, message = "Description must be at least 11 symbols!")
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
}
