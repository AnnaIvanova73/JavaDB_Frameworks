package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "competition_type")
public class CompetitionType extends BaseEntity {
    private String type;
    private Set<Competition> competition;

    public CompetitionType() {
    }

    @Column(name = "type")
    public String getName() {
        return type;
    }

    public void setName(String name) {
        this.type = name;
    }

    @OneToMany(mappedBy = "competitionType",targetEntity = Competition.class)
    public Set<Competition> getCompetition() {
        return competition;
    }


    public void setCompetition(Set<Competition> competitionTypes) {
        this.competition = competitionTypes;
    }
}
