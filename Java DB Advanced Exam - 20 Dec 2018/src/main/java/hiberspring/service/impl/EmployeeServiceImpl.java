package hiberspring.service.impl;

import hiberspring.domain.dtos.xmls.employee.EmployeeImportDto;
import hiberspring.domain.dtos.xmls.employee.EmployeeRootImportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeService;
import hiberspring.util.fileutil.FileUtil;
import hiberspring.util.validation.ValidationUtil;
import hiberspring.util.xmlparser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static hiberspring.common.Constants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_PATH = "src/main/resources/files/employees.xml";

    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final EmployeeCardRepository employeeCardRepository;


    public EmployeeServiceImpl(ModelMapper modelMapper,
                               XmlParser xmlParser,
                               ValidationUtil validationUtil,
                               StringBuilder sb,
                               FileUtil fileUtil,
                               EmployeeRepository employeeRepository,
                               BranchRepository branchRepository,
                               EmployeeCardRepository employeeCardRepository) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.employeeCardRepository = employeeCardRepository;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_PATH);
    }

    @Override
    public String importEmployees() throws JAXBException {
        this.sb.setLength(0);

        EmployeeRootImportDto rootDto =
                this.xmlParser.parseXml(EmployeeRootImportDto.class, EMPLOYEE_PATH);

        for (EmployeeImportDto dto : rootDto.getEmployees()) {

            if (!this.validationUtil.isValid(dto)) {
                collectInvalidMsg();
                continue;
            }
            Optional<EmployeeCard> byNumberLike =
                    this.employeeCardRepository.findByNumberLike(dto.getCardNumber());

            Optional<Branch> byNameLike = this.branchRepository.findByNameLike(dto.getBranchName().toString());

            Optional<Employee> byCard_number = this.employeeRepository.findByCard_Number(dto.getCardNumber());

            if (byNumberLike.isEmpty() || byNameLike.isEmpty() || byCard_number.isPresent()) {
                collectInvalidMsg();
                continue;
            }
            Employee empEntity = this.modelMapper.map(dto, Employee.class);
            empEntity.setBranch(byNameLike.get());
            empEntity.setCard(byNumberLike.get());
            this.employeeRepository.saveAndFlush(empEntity);
            this.sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            empEntity.getClass().getSimpleName(),
                            empEntity.getFirstName() + " " + empEntity.getLastName()))
                    .append(System.lineSeparator());

        }


        return this.sb.toString().trim();
    }

    private void collectInvalidMsg() {
        this.sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
    }

    @Override
    public String exportProductiveEmployees() {
        this.sb.setLength(0);

        Set<Employee> byBranch = this.employeeRepository.findByBranch();

        for (Employee branch : byBranch) {

            sb.append(String.format("Name: %s", branch.getFirstName() + " " + branch.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("Position %s", branch.getPosition()))
                    .append(System.lineSeparator())
                    .append(String.format("Card Number: %s", branch.getCard().getNumber()))
                    .append(System.lineSeparator());
        }
        return this.sb.toString().trim();
    }
}
