package softuni.gameshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gameshop.domain.dtos.UserDtoLogged;
import softuni.gameshop.domain.dtos.UserDtoShoppingCart;
import softuni.gameshop.domain.dtos.UserLoginDto;
import softuni.gameshop.domain.dtos.seed.UserRegisterDto;
import softuni.gameshop.domain.dtos.view.GamesDto;
import softuni.gameshop.domain.entities.Game;
import softuni.gameshop.domain.entities.User;
import softuni.gameshop.domain.enums.Role;
import softuni.gameshop.domain.repo.GamesRepository;
import softuni.gameshop.domain.repo.UsersRepository;
import softuni.gameshop.services.UserService;
import softuni.gameshop.utils.ValidatorUtilImpl;

import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final ValidatorUtilImpl validatorUtil;
    private final StringBuilder sb;
    private final GamesRepository gamesRepository;


    private final UsersRepository usersRepository;
    private UserDtoLogged loggedUser;
    private UserDtoShoppingCart shoppingCart;

    @Autowired
    public UserServiceImpl(ModelMapper mapper,
                           UsersRepository usersRepository,
                           ValidatorUtilImpl validatorUtil,
                           StringBuilder sb,
                           GamesRepository gamesRepository) {
        this.mapper = mapper;
        this.usersRepository = usersRepository;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
        this.gamesRepository = gamesRepository;
    }


    @Override
    public String registerUser(UserRegisterDto userDtoSeed) {
        this.sb.setLength(0);
        if (!userDtoSeed.getPassword().equals(userDtoSeed.getConfirmPassword())) {
            return "Passwords doesnt match";
        }
        if (!this.validatorUtil.isValid(userDtoSeed)) {
            this.validatorUtil.violations(userDtoSeed)
                    .forEach(m -> sb.append(m.getMessage()).append(System.lineSeparator()));
            return this.sb.toString().trim();
        }

        User userRepo = this.usersRepository.
                findUserByEmailAndPassword(userDtoSeed.getEmail(), userDtoSeed.getConfirmPassword());
        if (userRepo != null) {
            return "This user is already registered";
        }
        Role role = Role.User;
        if (this.usersRepository.count() == 0) {
            role = Role.Administrator;
        }
        User user = this.mapper.map(userDtoSeed, User.class);
        user.setRole(role);
        this.usersRepository.saveAndFlush(user);

        sb.append(String.format("%s was registered", userDtoSeed.getFullName()));
        return this.sb.toString().trim();
    }

    @Override
    public String tryToLoggIn(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();
        User userEntity =
                this.usersRepository.findUserByEmailAndPassword(email, password);


        if (userEntity == null) {
            return "Incorrect username / password";
        }


        if (this.loggedUser != null) {
            return "Already logged";
        }

        this.loggedUser = this.mapper.map(userEntity, UserDtoLogged.class);
        this.shoppingCart = new UserDtoShoppingCart(this.loggedUser.getEmail(), this.loggedUser.getFullName());
        return String.format("Successfully logged in %s", userEntity.getFullName());
    }

    @Override
    public String logout() {
        if (verifyUser()) return "Cannot log out. No user was logged in.";
        String fullName = this.loggedUser.getFullName();
        this.loggedUser = null;
        this.shoppingCart = null;
        return String.format("User %s successfully logged out, tho' he loosed all info from shopping cart", fullName);
    }

    @Override
    public String addItem(String title) {
        if (verifyUser()) return "You should logg user first" + System.lineSeparator() + "Here you can logg Ivan:"
                + System.lineSeparator() + "LoginUser|ivan@ivan.com|Ivan12";
        Game gameByTitle = this.gamesRepository.findGameByTitle(title);
        if (gameByTitle == null) {
            return "We do not have this game in catalog.Add games for tests";
        }
        GamesDto setGameView = this.mapper.map(gameByTitle, GamesDto.class);
        boolean isAdded = this.shoppingCart.addGame(setGameView);

        String out = "asd";
        if (!isAdded) {
            out = "User already had this game!";
        } else {
            out = String.format("%s added to cart.", gameByTitle.getTitle());
        }
        return out;
    }

    @Override
    public String removeItem(String title) {
        if (verifyUser()) return "You should logg user first" + System.lineSeparator() + "Here you can logg Ivan:"
                + System.lineSeparator() + "LoginUser|ivan@ivan.com|Ivan12";
        Game gameByTitle = this.gamesRepository.findGameByTitle(title);
        if (gameByTitle == null) {
            return "We do not have this game in catalog.";
        }
        GamesDto setGameView = this.mapper.map(gameByTitle, GamesDto.class);
        boolean isAdded = this.shoppingCart.removeGame(setGameView);

        String out = "";
        if (!isAdded) {
            out = "This game is not in the cart.It can't be removed";
        } else {
            out = String.format("%s removed", gameByTitle.getTitle());
        }
        return out;

    }

    @Override

    public String buyItem() {
        sb.setLength(0);
        if (verifyUser()) return "You should logg user first" + System.lineSeparator() + "Here you can logg Ivan:"
                + System.lineSeparator() + "LoginUser|ivan@ivan.com|Ivan12";

        Set<GamesDto> games = this.shoppingCart.getGames();
        if (games.size() == 0) {
            return "Empty cart!Please add games";
        }
        String email = this.loggedUser.getEmail();
        User myUser = this.usersRepository.findUserByEmail(email);

        sb.append("Successfully bought games: ").append(System.lineSeparator());

        for (GamesDto gamesDto : games) {
            Game game = this.mapper.map(gamesDto, Game.class);

            if (myUser.getGames().isEmpty()) {
                sb.append(gamesDto.toString())
                        .append(System.lineSeparator());
                myUser.getGames().add(game);
            } else {

                Optional<Game> findGame =
                        myUser.getGames()
                                .stream()
                                .filter(g -> g.getTitle()
                                        .equals(gamesDto.getTitle())).findAny();

                if (findGame.isEmpty()) {
                    sb.append(gamesDto.toString())
                            .append(System.lineSeparator());
                    myUser.getGames().add(game);

                }else{
                    sb.append(String.format("User had game %s", gamesDto.getTitle()))
                            .append(System.lineSeparator());
                }
            }
        }

        this.usersRepository.saveAndFlush(myUser);
        this.shoppingCart.emptyCart();
        return sb.toString().trim();

    }

    @Override
    public String printLoggedUserOwnedGames() {
        if (this.loggedUser == null) {
            return "You should be logged!";
        }
        User userByEmail = this.usersRepository.findUserByEmail(this.loggedUser.getEmail());
        if (userByEmail.getGames().isEmpty()) {
            return "Use task five and buy some games first!";
        }

        sb.setLength(0);
        userByEmail.getGames().forEach(g -> sb.append(g.getTitle()).append(System.lineSeparator()));
        return sb.toString().trim();
    }

    private boolean verifyUser() {
        return this.loggedUser == null;
    }
}
