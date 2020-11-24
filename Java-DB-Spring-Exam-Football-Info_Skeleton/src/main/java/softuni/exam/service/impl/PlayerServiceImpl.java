package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.jsons.PlayerImportDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.PlayerService;
import softuni.exam.util.fileutil.FileUtil;
import softuni.exam.util.validator.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final static String PLAYERS_PATH = "src/main/resources/files/json/players.json";

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository,
                             ModelMapper modelMapper,
                             Gson gson,
                             ValidatorUtil validatorUtil, StringBuilder sb,
                             FileUtil fileUtil, TeamRepository teamRepository, PictureRepository pictureRepository) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public String importPlayers() throws IOException {
        this.sb.setLength(0);
        PlayerImportDto[] playerImportDtos = this.gson.fromJson(readPlayersJsonFile(), PlayerImportDto[].class);

        for (PlayerImportDto playerImportDto : playerImportDtos) {

            boolean isValid = Arrays.stream(Position.values())
                    .map(Enum::name)
                    .anyMatch(p -> p.equals(playerImportDto.getPosition()));

            if(!this.validatorUtil.isValid(playerImportDto) || !isValid){
                this.sb.append("Invalid player").append(System.lineSeparator());
                continue;
            }
            Position position = Position.valueOf(playerImportDto.getPosition());

            Optional<Team> team = this.teamRepository.findByName(playerImportDto.getTeam().getName());
            Optional<Picture> picture= this.pictureRepository.findPictureByUrl(playerImportDto.getTeam().getPicture().getUrl());

            Player playerEntity = this.modelMapper.map(playerImportDto, Player.class);
            playerEntity.setPosition(position);
            playerEntity.setTeam(team.get());
            playerEntity.setPicture(picture.get());
            this.playerRepository.saveAndFlush(playerEntity);

            sb.append(String.format("Successfully imported player - %s %s",
                    playerImportDto.getFirstName(),playerImportDto.getLastName()))
                    .append(System.lineSeparator());
        }
        return this.sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(PLAYERS_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() throws IOException {
       //

        this.sb.setLength(0);
        Set<Player> northHub =
                this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(new BigDecimal(100000));

        for (Player player : northHub) {
            sb.append(String.format("Player name: %s %s",player.getFirstName(),player.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("\tNumber: %s",player.getNumber())).append(System.lineSeparator())
                    .append(String.format("\tSalary: %.2f",player.getSalary())).append(System.lineSeparator())
                    .append(String.format("\tTeam: %s",player.getTeam().getName()))
                    .append(System.lineSeparator());

        }

        return this.sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        this.sb.setLength(0);
        Set<Player> northHub =
                this.playerRepository.findAllByTeamNameOrderById("North Hub");
        this.sb.append("Team: North Hub") .append(System.lineSeparator());
        for (Player player : northHub) {
            sb.append(String.format("\tPlayer name: %s %s - %s",player.getFirstName(),player.getLastName(),
                    player.getPosition()))
                    .append(System.lineSeparator())
                    .append(String.format("\tNumber: %s",player.getNumber())).append(System.lineSeparator());

        }

        return this.sb.toString().trim();
    }
}
