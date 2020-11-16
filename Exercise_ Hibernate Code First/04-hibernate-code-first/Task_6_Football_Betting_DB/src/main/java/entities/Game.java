package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    private Team homeTeam;
    private Team awayTeam;
    private int homeGoals;
    private int awayGoals;
    private LocalDateTime dateAndTime;
    private double homeTownWinBet;
    private double awayTownWinBet;
    private double drawGameBet;
    private Round round;
    private Competition competition;

    public Game() {
    }

    @ManyToOne()
    @JoinColumn(name = "home_team", referencedColumnName = "id")
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
    @ManyToOne()
    @JoinColumn(name = "away_team", referencedColumnName = "id")
    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
    @Column(name = "home_team_goals")
    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }
    @Column(name = "away_team_goals")
    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }
    @Column(name = "date_name")
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
    @Column(name = "home_team_win_bet_rate")
    public double getHomeTownWinBet() {
        return homeTownWinBet;
    }

    public void setHomeTownWinBet(double homeTownWinBet) {
        this.homeTownWinBet = homeTownWinBet;
    }
    @Column(name = "away_team_win_bet_rate")
    public double getAwayTownWinBet() {
        return awayTownWinBet;
    }

    public void setAwayTownWinBet(double awayTownWinBet) {
        this.awayTownWinBet = awayTownWinBet;
    }
    @Column(name = "draw_game_bet_rate")
    public double getDrawGameBet() {
        return drawGameBet;
    }

    public void setDrawGameBet(double drawGameBet) {
        this.drawGameBet = drawGameBet;
    }

    @ManyToOne()
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    @ManyToOne
    @JoinColumn(name = "competition_id",referencedColumnName = "id")
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
