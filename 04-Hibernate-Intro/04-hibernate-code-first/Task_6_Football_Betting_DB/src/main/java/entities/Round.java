package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rounds")
public class Round extends BaseEntity {
    private String name;
    private Set<Game> game;

    public Round() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "round",targetEntity = Game.class)
    public Set<Game> getGame() {
        return game;
    }

    public void setGame(Set<Game> game) {
        this.game = game;
    }
}
