package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.jsons.TownImportDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.fileutil.FileUtil;
import hiberspring.util.validation.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static hiberspring.common.Constants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class TownServiceImpl implements TownService {

    private final static String PATH_TO_FILES = "src/main/resources/files/towns.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final StringBuilder sb;
    private final FileUtil fileUtil;

    @Autowired
    public TownServiceImpl(ModelMapper modelMapper,
                           Gson gson,
                           ValidationUtil validationUtil,
                           TownRepository townRepository,
                           StringBuilder sb,
                           FileUtil fileUtil) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.sb = sb;
        this.fileUtil = fileUtil;
    }


    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(PATH_TO_FILES);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        this.sb.setLength(0);

        TownImportDto[] townDtos = this.gson.fromJson(readTownsJsonFile(), TownImportDto[].class);

        for (TownImportDto townDto : townDtos) {

            if (!this.validationUtil.isValid(townDto)) {
                this.sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Town townEntity = this.modelMapper.map(townDto, Town.class);
            this.townRepository.saveAndFlush(townEntity);

            this.sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, townEntity.getClass().getSimpleName(), townDto.getName()))
                    .append(System.lineSeparator());

        }

        return this.sb.toString().trim();
    }
}
