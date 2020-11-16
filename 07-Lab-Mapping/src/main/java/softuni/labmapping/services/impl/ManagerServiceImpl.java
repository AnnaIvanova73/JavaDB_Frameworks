package softuni.labmapping.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.labmapping.domain.dtos.view.EmployeeViewDto;
import softuni.labmapping.domain.dtos.view.ManagerView;
import softuni.labmapping.domain.entities.Employee;
import softuni.labmapping.domain.repo.EmployeeRepository;
import softuni.labmapping.services.EmployeeService;
import softuni.labmapping.services.ManagerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


@Service
public class ManagerServiceImpl implements ManagerService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public ManagerServiceImpl(EmployeeRepository employeeRepository,
                              EmployeeService employeeService,
                              ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void setManagers() {
        List<Employee> all = this.employeeRepository.findAll();
        for (Employee employee1 : all) {
            employee1.setManager(getManager());
            if (employee1.getId() == 1 || employee1.getId() == 2) {
                employee1.setManager(null);
            }
            this.employeeRepository.saveAndFlush(employee1);
        }
    }

    @Override
    public List<ManagerView> printAllManagersWithTheirEmployees() {
        List<ManagerView> managerViews = new ArrayList<>();
        List<Employee> allByManagerNotNull = this.employeeRepository.findAllByManagerNull();
        for (Employee employee : allByManagerNotNull) {
            Set<EmployeeViewDto> allEmployeesByManagerId =
                    this.employeeService.getAllEmployeesByManagerId(employee.getId());
            ManagerView managerView = this.modelMapper.map(employee, ManagerView.class);
            managerView.setEmployees(allEmployeesByManagerId);
            managerViews.add(managerView);
        }

        return managerViews;
    }

    private Employee getManager() {
        Random r = new Random();
        int low = 1;
        int high = 3;
        int result = r.nextInt(high - low) + low;
        return this.employeeRepository.getOne((long) result);
    }
}
