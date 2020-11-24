package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.xmls.PictureImportDto;
import softuni.exam.domain.dtos.xmls.TeamImportDto;
import softuni.exam.domain.dtos.xmls.TeamRootImportDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.TeamService;
import softuni.exam.util.fileutil.FileUtil;
import softuni.exam.util.validator.ValidatorUtil;
import softuni.exam.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final static String TEAMS_PATH = "src/main/resources/files/xml/teams.xml";


    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final TeamRepository teamRepository;
    private final XmlParser xmlParser;
    private final StringBuilder sb;
    private final PictureRepository pictureRepository;

    public TeamServiceImpl(ModelMapper modelMapper,
                           FileUtil fileUtil,
                           ValidatorUtil validatorUtil,
                           TeamRepository teamRepository,
                           XmlParser xmlParser,
                           StringBuilder sb,
                           PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.teamRepository = teamRepository;
        this.xmlParser = xmlParser;
        this.sb = sb;
        this.pictureRepository = pictureRepository;
    }


    @Override
    public String importTeams() throws JAXBException {
        this.sb.setLength(0);
        TeamRootImportDto teamRoot = this.xmlParser.parseXml(TeamRootImportDto.class,TEAMS_PATH);

        for (TeamImportDto dto : teamRoot.getTeams()) {

            PictureImportDto objDto = dto.getUrl();

           Optional<Picture> pictureByUrl = this.pictureRepository.findPictureByUrl(objDto.getUrl());


            if( pictureByUrl.isEmpty() || !this.validatorUtil.isValid(dto)){
                sb.append("Invalid team").append(System.lineSeparator());
                continue;
            }


            Team teamEntity = this.modelMapper.map(dto, Team.class);
            teamEntity.setPicture(pictureByUrl.get());
            this.teamRepository.saveAndFlush(teamEntity);
            sb.append(String.format("Successfully imported - %s", dto.getName()))
                    .append(System.lineSeparator());
        }
        return this.sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return this.fileUtil.readFile(TEAMS_PATH);
    }
}
