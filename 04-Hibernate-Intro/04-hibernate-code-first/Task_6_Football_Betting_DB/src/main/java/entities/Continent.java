package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "continents")
public class Continent extends BaseEntity {

    private String name;
    private Set<Country> countries;


    public Continent() {
    }



    @Column(unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToMany(mappedBy = "continents",targetEntity = Country.class)
    public Set<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

}
