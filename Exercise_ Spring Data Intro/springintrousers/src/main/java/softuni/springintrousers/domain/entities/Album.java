package softuni.springintrousers.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {
    private String name;
    private String backgroundColor;
    private Boolean isPublic;
    private Set<Picture> pictures;
    private User user;

    public Album() {
    }


    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "background_color")
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Column(name = "public_or_not", nullable = false, columnDefinition = "TINYINT(1)")
    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @OneToMany(mappedBy = "album", targetEntity = Picture.class, fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
