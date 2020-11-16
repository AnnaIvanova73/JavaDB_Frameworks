package entities;

import entities.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    private String name;
    private byte[] logo;
    private String letterInitials;
    private Color primaryKitColor;
    private Color secondaryKitColor;
    private Town town;
    private BigDecimal budget;
    private Set<Game> homeTeams;
    private Set<Game> awayTeam;

    public Team() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "logo")
    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    @Column(name = "initials", columnDefinition = "CHAR", length = 3)
    public String getLetterInitials() {
        return letterInitials;
    }

    public void setLetterInitials(String letterInitials) {
        this.letterInitials = letterInitials;
    }

    @ManyToOne()
    @JoinColumn(name = "primary_color_id", referencedColumnName = "id")
    public Color getPrimaryKitColor() {
        return primaryKitColor;
    }

    public void setPrimaryKitColor(Color primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
    }

    @ManyToOne()
    @JoinColumn(name = "secondary_color_id", referencedColumnName = "id")
    public Color getSecondaryKitColor() {
        return secondaryKitColor;
    }

    public void setSecondaryKitColor(Color secondaryKitColor) {
        this.secondaryKitColor = secondaryKitColor;
    }

    @ManyToOne()
    @JoinColumn(name = "town_id",referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Column(name = "budget")
    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    @OneToMany(mappedBy = "homeTeam", targetEntity = Game.class)
    public Set<Game> getHomeTeams() {
        return homeTeams;
    }

    public void setHomeTeams(Set<Game> homeTeams) {
        this.homeTeams = homeTeams;
    }

    @OneToMany(mappedBy = "awayTeam", targetEntity = Game.class)
    public Set<Game> getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Set<Game> awayTeam) {
        this.awayTeam = awayTeam;
    }
}
