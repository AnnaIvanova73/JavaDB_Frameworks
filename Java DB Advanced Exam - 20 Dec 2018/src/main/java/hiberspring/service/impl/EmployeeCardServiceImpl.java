package hiberspring.service.impl;


import com.google.gson.Gson;
import hiberspring.domain.dtos.jsons.EmployeeCardImportDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.fileutil.FileUtil;
import hiberspring.util.validation.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static hiberspring.common.Constants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private static final String EMPLOYEE_CARD_PATH = "src/main/resources/files/employee-cards.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final EmployeeCardRepository cardRepository;

    public EmployeeCardServiceImpl(ModelMapper modelMapper,
                                   Gson gson,
                                   ValidationUtil validationUtil,
                                   StringBuilder sb,
                                   FileUtil fileUtil,
                                   EmployeeCardRepository cardRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.cardRepository = cardRepository;
    }


    @Override
    public Boolean employeeCardsAreImported() {
        return this.cardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_CARD_PATH);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        this.sb.setLength(0);

        EmployeeCardImportDto[] rootDtos = this.gson.fromJson(readEmployeeCardsJsonFile(), EmployeeCardImportDto[].class);
        for (EmployeeCardImportDto dto : rootDtos) {
            Optional<EmployeeCard> byNumberLike = this.cardRepository.findByNumberLike(dto.getNumber());

            if(!this.validationUtil.isValid(dto) || byNumberLike.isPresent()){
            this.sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            EmployeeCard ecEntity = this.modelMapper.map(dto, EmployeeCard.class);
            this.cardRepository.saveAndFlush(ecEntity);

            String className = StringUtils.join(
                    StringUtils.splitByCharacterTypeCamelCase(ecEntity.getClass().getSimpleName())
                    , StringUtils.SPACE
            );

            this.sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            className,ecEntity.getNumber()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
