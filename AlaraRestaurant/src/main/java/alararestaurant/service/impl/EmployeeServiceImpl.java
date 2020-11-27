package alararestaurant.service.impl;

import alararestaurant.domain.dtos.jsons.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.service.EmployeeService;
import alararestaurant.util.fileutil.FileUtil;
import alararestaurant.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import static alararestaurant.config.constant.ConstantMsg.INVALID_MSG;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_PATH = "src/main/resources/files/employees.json";


    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final PositionRepository positionRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ModelMapper modelMapper,
                               StringBuilder sb,
                               FileUtil fileUtil,
                               Gson gson,
                               ValidationUtil validationUtil1,
                               PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil1;
        this.positionRepository = positionRepository;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_PATH);

    }

    @Override
    public String importEmployees(String employees) throws IOException {
        this.sb.setLength(0);

        EmployeeImportDto[] employeeImportDtos
                = this.gson.fromJson(readEmployeesJsonFile(), EmployeeImportDto[].class);

        for (EmployeeImportDto empDto : employeeImportDtos) {
            String namePosition = empDto.getPosition();

            if (!this.validationUtil.isValid(empDto) ||
                    namePosition.equalsIgnoreCase("invalid")) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }


            Employee empEntity
                    = this.modelMapper.map(empDto, Employee.class);


            Position position = new Position();
            position.setName(namePosition);
            Position findPosition = this.positionRepository.
                    findFirstByNameLike(namePosition)
                    .stream().findFirst().orElse(position);


            this.positionRepository.saveAndFlush(findPosition);
            empEntity.setPosition(findPosition);
            this.employeeRepository.saveAndFlush(empEntity);


            this.sb.append(String.format("%s successfully imported."
                    ,empEntity.getName())).append(System.lineSeparator());
        }


        return this.sb.toString().trim();
    }
}
