package softuni.gameshop.runner;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.gameshop.domain.dtos.EditGameDto;
import softuni.gameshop.domain.dtos.UserLoginDto;
import softuni.gameshop.domain.dtos.seed.AddGameDto;
import softuni.gameshop.domain.dtos.seed.UserRegisterDto;
import softuni.gameshop.domain.dtos.view.DtoSingleGameView;
import softuni.gameshop.services.EditGameService;
import softuni.gameshop.services.GameService;
import softuni.gameshop.services.UserService;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Objects;

import static softuni.gameshop.constants.OutMsg.OWNED_GAMES;


@Component
public class CmdRunner implements CommandLineRunner {
    private final BufferedReader reader;
    private final UserService userService;
    private final DateTimeFormatter formatter;
    private final GameService gameService;
    private final EditGameService editGameService;



    public CmdRunner(BufferedReader reader,
                     UserService userService,
                     DateTimeFormatter formatter,
                     GameService gameService,
                     EditGameService editGameService) {
        this.reader = reader;
        this.userService = userService;
        this.formatter = formatter;
        this.gameService = gameService;
        this.editGameService = editGameService;
    }

    @Override
    public void run(String... args) throws Exception {
        String welcome = String.format("Hi,welcome! Please enter input by document format or exit for end%n" +
                "Everything is implemented with switch and while%n" +
                "You should know the drill");
        System.err.println(welcome);


        String line;
        while (!(line = this.reader.readLine().trim()).equalsIgnoreCase("exit")) {
            String[] tokens = line.split("\\|");

            switch (tokens[0]) {

                case "RegisterUser":
                    UserRegisterDto userRegisterDto =
                            new UserRegisterDto(tokens[1], tokens[2], tokens[3], tokens[4]);

                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(tokens[1], tokens[2]);
                    System.out.println(this.userService.tryToLoggIn(userLoginDto));
                    break;
                case "Logout":
                    System.out.println(this.userService.logout());
                    break;
                case "AddGame":
                    AddGameDto addGameDto = new AddGameDto(tokens[1],
                            new BigDecimal(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            tokens[4], tokens[5], tokens[6],
                            LocalDate.parse(tokens[7], formatter));
                    System.out.println(this.gameService.addGame(addGameDto));
                    break;
                case "EditGame":
                    EditGameDto editGameDto = createDtoForGameEditing(tokens);
                    boolean isInCatalog = this.editGameService.checkExistence(editGameDto.getId());
                    if (!isInCatalog) {
                        System.out.println("This game ID dont match any game in catalog");
                        break;
                    }
                    System.out.println(this.editGameService.editGame(editGameDto));
                    break;
                case "DeleteGame":
                    System.out.println(this.editGameService.deleteGame(Long.parseLong(tokens[1])));
                    break;

                case "AllGames":
                    System.out.println(this.gameService.printAllGamesWithTitleAndPrice());
                    break;
                case "DetailGame":
                    DtoSingleGameView dtoSingleGameView = this.gameService.printAllAboutGameByTitle(tokens[1]);
                    if (dtoSingleGameView != null) {
                        System.out.println(dtoSingleGameView);
                    }
                    break;
                case "OwnedGames":
                    System.out.println(OWNED_GAMES);
                    System.out.println(this.userService.printLoggedUserOwnedGames());
                    break;
                case "AddItem":
                    System.out.println(this.userService.addItem(tokens[1]));
                    break;
                case "RemoveItem":
                    System.out.println(this.userService.removeItem(tokens[1]));
                    break;
                case "BuyItem":
                    System.out.println(this.userService.buyItem());
                    break;
                default:
                    System.out.println("No such option");
                    break;

            }

            System.err.println(welcome);
        }

        System.exit(0);
    }

    @NotNull
    private EditGameDto createDtoForGameEditing(String[] tokens) {
        ArrayDeque<String> queue = this.editGameService.extractValues(tokens[2], tokens[3]);
        return new EditGameDto(Long.parseLong(tokens[1]),
                new BigDecimal(Objects.requireNonNull(queue.poll())),
                Double.parseDouble(Objects.requireNonNull(queue.poll())));
    }
}
