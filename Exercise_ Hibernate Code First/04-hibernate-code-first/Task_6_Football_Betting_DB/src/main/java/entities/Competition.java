package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity {
    private String name;
    private CompetitionType competitionType;
    private Set<Game> games;

    public Competition() {
    }

    @Column(name = "name")
    public String getType() {
        return name;
    }

    public void setType(String type) {
        this.name = type;
    }

    @ManyToOne
    @JoinColumn(name = "competition_type_id",referencedColumnName = "id")
    public CompetitionType  getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType type) {
        this.competitionType = type;
    }


    @OneToMany(mappedBy = "competition",targetEntity = Game.class)
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
