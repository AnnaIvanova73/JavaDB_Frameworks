package softuni.gameshop.services.impl;

import org.springframework.stereotype.Service;
import softuni.gameshop.domain.dtos.EditGameDto;
import softuni.gameshop.domain.entities.Game;
import softuni.gameshop.domain.repo.GamesRepository;
import softuni.gameshop.services.EditGameService;

import java.util.ArrayDeque;


@Service
public class EditGameServiceImpl implements EditGameService {

    private final GamesRepository gamesRepository;

    public EditGameServiceImpl(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Override
    public String editGame(EditGameDto editDto) {
        Game gameById = findGame(editDto);
        gameById.setPrice(editDto.getPrice());
        gameById.setSize(editDto.getSize());
        this.gamesRepository.saveAndFlush(gameById);
        System.out.println(
        );
        return String.format("Edited %s",gameById.getTitle());
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
        if(!checkExistence(id)){
            return "No such game!";
        }

        String name = this.gamesRepository.findGameById(id).getTitle();
        this.gamesRepository.deleteById(id);

        return String.format("Deleted %s%n" +
                "Please bear in mind when you perform delete operation, that auto increment id doesnt reset itself " +
                "after deletion of last row",name);
    }

    private Game findGame(EditGameDto editGameDto) {
        return this.gamesRepository.findGameById(editGameDto.getId());
    }
}
