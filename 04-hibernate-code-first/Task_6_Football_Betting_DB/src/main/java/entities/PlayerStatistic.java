package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "player_statistics")
public class PlayerStatistic implements Serializable {

    private static final long SerialVersionUID=101L;
    //If you watch my code and wonder the hell is this, check this article -->
    // https://www.geeksforgeeks.org/serialversionuid-in-java/

    private Game game;
    private Player player;
    private int scoredGoals;
    private int assists;
    private short playedMinDuringGame;


    public PlayerStatistic() {
    }

    @Id
    @ManyToOne()
    @JoinColumn(name = "game_id", referencedColumnName = "id",nullable = false)
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    @Id
    @ManyToOne()
    @JoinColumn(name = "player_id", referencedColumnName = "id",nullable = false)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Column(name = "scored_goals", columnDefinition = "TINYINT")
    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    @Column(name = "assists", columnDefinition = "SMALLINT")
    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Column(name = "played_minutes", columnDefinition = "SMALLINT")
    public short getPlayedMinDuringGame() {
        return playedMinDuringGame;
    }

    public void setPlayedMinDuringGame(short playedMinDuringGame) {
        this.playedMinDuringGame = playedMinDuringGame;
    }
}
