package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "countries")
public class Country  {
    private String id;
    private String name;
    private Set<Continent> continents;

    public Country() {
    }

    @Id
    @Column(unique = true, nullable = false, length = 3)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(unique = true,length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ManyToMany
    @JoinTable(name = "countries_continents",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id"))
    public Set<Continent> getContinents() {
        return this.continents;
    }

    public void setContinents(Set<Continent> continents) {
        this.continents = continents;
    }
}
