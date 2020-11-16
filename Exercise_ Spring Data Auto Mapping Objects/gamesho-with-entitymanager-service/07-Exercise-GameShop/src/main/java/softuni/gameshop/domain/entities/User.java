package softuni.gameshop.domain.entities;


import softuni.gameshop.domain.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String email;
    private String password;
    private String fullName;
    private Role role;
    private Set<Game> games; //TODO

    public User() {
    }

    @Column(name = "email",unique = true, nullable = false)
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password",nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "full_name",nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name ="user_game",joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns =@JoinColumn(name = "game_id"))
    public Set<Game> getGames() {
        return this.games;
    }
    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
