package softuni.gameshop.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    private String title;
    private String trailer;
    private String  thumbnail;
    private double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;
    private Set<User> user;

    public Game() {
    }

    @Column(name = "title", unique = true)
    @Length(min = 3, max = 100) //inclusive or exclusive params in annotation?
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "trailers" ,nullable = false)
    @Length(min = 11, max = 11)
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    @Column(name = "thumbnail",columnDefinition = "TEXT")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Column(name = "size",columnDefinition = "INT(11) UNSIGNED")
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
    @Column(name = "price", columnDefinition = "INT(11) UNSIGNED")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "description")
    @Length(min = 20)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "release_date" )
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ManyToMany(mappedBy = "games",fetch = FetchType.EAGER)
    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
