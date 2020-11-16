package softuni.gameshop.services;

import softuni.gameshop.domain.dtos.seed.AddGameDto;
import softuni.gameshop.domain.dtos.view.DtoSingleGameView;

public interface GameService {

    String addGame(AddGameDto gameDto);
    String printAllGamesWithTitleAndPrice ();
    DtoSingleGameView printAllAboutGameByTitle(String title);

}
