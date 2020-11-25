package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.jsons.TownImportDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private final static String TOWNS_PATH = "src/main/resources/files/json/towns.json";

    private final Gson gson;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    public TownServiceImpl(Gson gson,
                           StringBuilder sb,
                           ValidationUtil validationUtil,
                           TownRepository townRepository,
                           ModelMapper modelMapper) {
        this.gson = gson;
        this.sb = sb;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return String.join("",
                Files.readAllLines(Path.of(TOWNS_PATH)));
    }

    @Override
    public String importTowns() throws IOException {
        this.sb.setLength(0);

        TownImportDto[] rootDto = this.gson.fromJson(readTownsFileContent(), TownImportDto[].class);

        System.out.println();
        for (TownImportDto currentDto : rootDto) {
            Optional<Town> byName = this.townRepository.findByName(currentDto.getName());

            if (!this.validationUtil.isValid(currentDto) || byName.isPresent()) {
                sb.append("Invalid Town").append(System.lineSeparator());
                continue;
            }

            Town town = this.modelMapper.map(currentDto, Town.class);
            this.townRepository.saveAndFlush(town);

            this.sb.append(String.format("Successfully imported Town %s - %d",
                    currentDto.getName(), currentDto.getPopulation())).append(System.lineSeparator());

        }
        return this.sb.toString().trim();
    }
}
