package entities;

import entities.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Entity
@Table(name = "colors")
public class Color extends BaseEntity {
    private String name;
    private Set<Team> usingAsPrimary;
    private Set<Team> usingAsSecondary;
    public Color() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @OneToMany(mappedBy = "primaryKitColor",targetEntity = Team.class)
    public Set<Team> getUsingAsPrimary() {
        return usingAsPrimary;
    }

    public void setUsingAsPrimary(Set<Team> usingAsPrimary) {
        this.usingAsPrimary = usingAsPrimary;
    }
    @OneToMany(mappedBy = "secondaryKitColor",targetEntity = Team.class)
    public Set<Team> getUsingAsSecondary() {
        return usingAsSecondary;
    }

    public void setUsingAsSecondary(Set<Team> usingAsSecondary) {
        this.usingAsSecondary = usingAsSecondary;
    }
}
