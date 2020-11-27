package app.ccb.services.impl;

import app.ccb.domain.dtos.jsons.EmployeeImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.services.EmployeeService;
import app.ccb.util.fileutil.FileUtil;
import app.ccb.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static app.ccb.config.constants.ConstantMsg.INVALID_MSG;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_PATH =
            "F:\\SpringData\\Java-DB-Spring-Data-Exam-ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\employees.json";


    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    @Autowired
    public EmployeeServiceImpl(FileUtil fileUtil,
                               Gson gson,
                               ModelMapper modelMapper,
                               StringBuilder sb,
                               ValidationUtil validationUtil,
                               EmployeeRepository employeeRepository,
                               BranchRepository branchRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
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
        EmployeeImportDto[] employeeImportDtos = this.gson.fromJson(readEmployeesJsonFile(), EmployeeImportDto[].class);

        for (EmployeeImportDto employeeDto : employeeImportDtos) {

            if (!this.validationUtil.isValid(employeeDto)) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }

            String[] tokens = employeeDto.getFullName().split("\\s+");
            Employee employeeEntity = this.modelMapper.map(employeeDto, Employee.class);
            employeeEntity.setFirstName(tokens[0]);
            employeeEntity.setLastName(tokens[1]);
            Optional<Branch> branch = this.branchRepository.findByNameLike(employeeDto.getBranch());
            employeeEntity.setBranch(branch.get());
            this.employeeRepository.saveAndFlush(employeeEntity);
            this.sb.append(String.format("Successfully imported %s - %s.",
                    employeeEntity.getClass().getSimpleName(),
                    employeeDto.getFullName())).append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }

    @Override
    public String exportTopEmployees() {
     this.sb.setLength(0);
        Set<Employee> employeeAndClients =
                this.employeeRepository.findEmployeeAndClients();

        for (Employee empCl : employeeAndClients) {
            sb.append(String.format("Employee: %s ,Employee Id: %s , Count Clients: %d",
                    empCl.getFirstName() + " " + empCl.getLastName(),
                    empCl.getId(),empCl.getClients().size())).append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }
}
