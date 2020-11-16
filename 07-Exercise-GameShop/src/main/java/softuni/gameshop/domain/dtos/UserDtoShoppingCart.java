package softuni.gameshop.domain.dtos;

import softuni.gameshop.domain.dtos.view.GamesDto;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
public class UserDtoShoppingCart {
    private String email;
    private String password;
    private Set<GamesDto> games;


    public UserDtoShoppingCart() {
    }

    public UserDtoShoppingCart(String email, String password) {
        this.email = email;
        this.password = password;
        this.games = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<GamesDto> getGames() {
        return games;
    }

    public void setGames(Set<GamesDto> games) {
        this.games = games;
    }

    public boolean addGame(GamesDto game) {
        boolean contains = this.games.contains(game);
        if (this.games.contains(game)) {
            return false;
        }
        this.games.add(game);
        return true;
    }

    public boolean removeGame(GamesDto game) {
        if (!this.games.contains(game)) {
            return false;
        }
        this.games.remove(game);
        return true;
    }


    public void emptyCart() {
        this.games.clear();
    }
}
