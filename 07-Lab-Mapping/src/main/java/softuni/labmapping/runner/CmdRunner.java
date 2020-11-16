package softuni.labmapping.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.labmapping.domain.dtos.seed.AddressSeedDto;
import softuni.labmapping.domain.dtos.seed.EmployeeSeedDto;
import softuni.labmapping.domain.dtos.view.EmployeeViewDto;
import softuni.labmapping.domain.dtos.view.ManagerView;
import softuni.labmapping.services.EmployeeService;
import softuni.labmapping.services.ManagerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Component
public class CmdRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final ManagerService managerService;

    public CmdRunner(EmployeeService employeeService, ManagerService managerService) {
        this.employeeService = employeeService;
        this.managerService = managerService;
    }

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        AddressSeedDto addressSeedDto = new AddressSeedDto("Sofia");
        AddressSeedDto addressSeedDto2 = new AddressSeedDto("Plovdiv");
        AddressSeedDto addressSeedDto3 = new AddressSeedDto("Burgas");

        EmployeeSeedDto employeeSeedDto1 = new EmployeeSeedDto("Steve",
                "Jobhsen", addressSeedDto, 10000,LocalDate.parse("1960-01-01"));
        EmployeeSeedDto employeeSeedDto2 = new EmployeeSeedDto("Stephen",
                "Bjorn", addressSeedDto2, 4300.00,LocalDate.parse("1960-01-01"));

        EmployeeSeedDto employeeSeedDto3 =
                new EmployeeSeedDto("Kirilyc", "Lefi", 4400.00, LocalDate.parse("1960-01-01"));
        EmployeeSeedDto employeeSeedDto4 =
                new EmployeeSeedDto("Carl", "Kormac", 10000, LocalDate.parse("1960-01-01"));
        EmployeeSeedDto employeeSeedDto5 =
                new EmployeeSeedDto("Jurgen", "Straus ", 1000.45, LocalDate.parse("1960-01-01"));
        EmployeeSeedDto employeeSeedDto6 =
                new EmployeeSeedDto("Moni", "Kozinac", 2030.99, LocalDate.parse("1960-01-01"));
        EmployeeSeedDto employeeSeedDto7 =
                new EmployeeSeedDto("Kopp", "Spidok", addressSeedDto3, 2000.21,LocalDate.parse("1999-01-01"));

        this.employeeService.seedDB(employeeSeedDto1);
        this.employeeService.seedDB(employeeSeedDto2);
        this.employeeService.seedDB(employeeSeedDto3);
        this.employeeService.seedDB(employeeSeedDto4);
        this.employeeService.seedDB(employeeSeedDto5);
        this.employeeService.seedDB(employeeSeedDto6);
        this.employeeService.seedDB(employeeSeedDto7);


        this.managerService.setManagers();

        List<ManagerView> managerViews = this.managerService.printAllManagersWithTheirEmployees();
        for (ManagerView managerView : managerViews) {
            System.err.println(managerView);
        }
        System.out.println();

        Set<EmployeeViewDto> allBornBefore
                = this.employeeService.getAllBornBefore(LocalDate.parse("1990-01-01"));

        for (EmployeeViewDto employeeViewDto : allBornBefore) {
            System.err.println(employeeViewDto.toString());
        }
    }
}
