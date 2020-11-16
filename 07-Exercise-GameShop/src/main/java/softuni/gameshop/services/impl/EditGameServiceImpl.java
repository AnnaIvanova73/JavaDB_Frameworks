package softuni.gameshop.services.impl;

import org.springframework.stereotype.Service;
import softuni.gameshop.domain.dtos.EditGameDto;
import softuni.gameshop.domain.entities.Game;
import softuni.gameshop.domain.entities.User;
import softuni.gameshop.domain.repo.GamesRepository;
import softuni.gameshop.domain.repo.UsersRepository;
import softuni.gameshop.services.EditGameService;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;


@Service
public class EditGameServiceImpl implements EditGameService {

    private final GamesRepository gamesRepository;
    private final UsersRepository usersRepository;

    public EditGameServiceImpl(GamesRepository gamesRepository, UsersRepository usersRepository) {
        this.gamesRepository = gamesRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public String editGame(EditGameDto editDto) {
        Game gameById = findGame(editDto);
        gameById.setPrice(editDto.getPrice());
        gameById.setSize(editDto.getSize());
        this.gamesRepository.saveAndFlush(gameById);
        System.out.println(
        );
        return String.format("Edited %s", gameById.getTitle());
    }

    @Override
    public boolean checkExistence(Long id) {
        return this.gamesRepository.existsById(id);
    }

    @Override
    public ArrayDeque<String> extractValues(String... input) {
        ArrayDeque<String> values = new ArrayDeque<>();
        for (String arg : input) {
            int i = arg.indexOf('=');
            String substring = arg.substring(i + 1);
            values.offer(substring);
        }
        return values;
    }

    @Override
    public String deleteGame(Long id) {
        if (!checkExistence(id)) {
            return "No such game!";
        }

        String name = this.gamesRepository.findGameById(id).getTitle();
        Optional<Game> byId = this.gamesRepository.findById(id);
        String title = byId.get().getTitle();
        Game gameByTitle = this.gamesRepository.findGameByTitle(title);
        this.gamesRepository.delete(gameByTitle);

        List<User> allByGamesIsNotNull = this.usersRepository.findAllByGamesIsNotNull();

        for (int i = 0; i < allByGamesIsNotNull.size(); i++) {
            User user = allByGamesIsNotNull.get(i);
            Optional<Game> any = user.getGames()
                    .stream().filter(e -> e.getTitle()
                            .equals(gameByTitle.getTitle())).findAny();
            if (any.isPresent()) {
                Game game = any.get();
                user.getGames().remove(game);
                this.usersRepository.saveAndFlush(user);
                this.gamesRepository.delete(gameByTitle);
            }
        }
        return String.format("Deleted %s%n" +
                "Please bear in mind when you perform delete operation, that auto increment id doesnt reset itself " +
                "after deletion of last row", name);
    }

    private Game findGame(EditGameDto editGameDto) {
        return this.gamesRepository.findGameById(editGameDto.getId());
    }
}
