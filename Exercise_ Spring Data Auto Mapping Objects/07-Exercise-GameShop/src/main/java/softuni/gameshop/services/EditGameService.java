package softuni.gameshop.services;

import softuni.gameshop.domain.dtos.EditGameDto;

import java.util.ArrayDeque;

public interface EditGameService {

    String editGame(EditGameDto editDto);
    boolean checkExistence(Long id);
    ArrayDeque<String> extractValues (String... input);
    String deleteGame (Long id);
}
