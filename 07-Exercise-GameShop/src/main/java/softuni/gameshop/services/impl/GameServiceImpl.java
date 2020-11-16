package softuni.gameshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gameshop.domain.dtos.seed.AddGameDto;
import softuni.gameshop.domain.dtos.view.DtoSingleGameView;
import softuni.gameshop.domain.dtos.view.DtoViewGameWithPriceAndTitle;
import softuni.gameshop.domain.entities.Game;
import softuni.gameshop.domain.repo.GamesRepository;
import softuni.gameshop.services.GameService;
import softuni.gameshop.utils.ValidatorUtil;

@Service
public class GameServiceImpl implements GameService {

    private final GamesRepository gameRepo;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final StringBuilder sb;

    @Autowired
    public GameServiceImpl(GamesRepository gameRepo,
                           ModelMapper mapper,
                           ValidatorUtil validatorUtil,
                           StringBuilder sb) {
        this.gameRepo = gameRepo;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
    }

    @Override
    public String addGame(AddGameDto gameDto) {
        sb.setLength(0);
        if(!this.validatorUtil.isValid(gameDto)){
            this.validatorUtil.violations(gameDto)
                    .forEach(err -> sb.append(err.getMessage())
                            .append(System.lineSeparator()));
            return this.sb.toString().trim();
        }
        Game gameByTrailer = this.gameRepo.findGameByTitle(gameDto.getTitle());
        if(gameByTrailer != null){
            return "This game is already in";
        }

        Game game = this.mapper.map(gameDto, Game.class);
        this.gameRepo.saveAndFlush(game);

        sb.append(String.format("Added %s",gameDto.getTitle()));
        return sb.toString().trim();
    }

    @Override
    public String printAllGamesWithTitleAndPrice() {
        sb.setLength(0);
        this.gameRepo.findAll()
                .stream()
                .map(g -> this.mapper.map(g, DtoViewGameWithPriceAndTitle.class))
                .forEach(gv -> sb.append(gv).append(System.lineSeparator()));

        return sb.toString().trim();
    }

    @Override
    public DtoSingleGameView printAllAboutGameByTitle(String title) {
        sb.setLength(0);
        Game gameByTitle = this.gameRepo.findGameByTitle(title);

        if(gameByTitle == null){
            System.out.println("No such game");
            return null;
        }
        DtoSingleGameView view = this.mapper.map(gameByTitle, DtoSingleGameView.class);
        System.out.println();
        return view;
    }


}
