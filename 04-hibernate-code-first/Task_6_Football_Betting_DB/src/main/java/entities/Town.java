package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    private String name;

    private Country country;

    private Set<Team> teams;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne()
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @OneToMany(mappedBy = "town", targetEntity = Team.class)
    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
