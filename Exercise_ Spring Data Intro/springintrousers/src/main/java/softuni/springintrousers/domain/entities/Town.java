package softuni.springintrousers.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    private String name;
    private String country;
    private Set<User> homeTown;
    private Set<User> currentTown;
    public Town() {
    }

    @Column(name = "town_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @OneToMany(mappedBy = "bornTown",targetEntity = User.class,
    fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<User> getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Set<User> homeTown) {
        this.homeTown = homeTown;
    }
    @OneToMany(mappedBy = "currentlyLiving",targetEntity = User.class,
            fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<User> getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Set<User> currentTown) {
        this.currentTown = currentTown;
    }
}
