package softuni.labmapping.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.labmapping.domain.dtos.seed.EmployeeSeedDto;
import softuni.labmapping.domain.dtos.view.EmployeeViewDto;
import softuni.labmapping.domain.entities.Employee;
import softuni.labmapping.domain.repo.EmployeeRepository;
import softuni.labmapping.services.EmployeeService;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(ModelMapper modelMapper,
                               EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void seedDB(EmployeeSeedDto employeeSeedDto) {
        Employee employee = createEntity(employeeSeedDto);
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee createEntity(EmployeeSeedDto employeeSeedDto) {
        return this.modelMapper.map(employeeSeedDto, Employee.class);
    }


    @Override
    public Set<EmployeeViewDto> getAllEmployeesByManagerId(Long id) {
        Set<Employee> employees = this.employeeRepository.findAllByManagerId(id);
        return getSetEmployeeViewDto(employees);
    }

    @Override
    public Set<EmployeeViewDto> getAllBornBefore(LocalDate localDate) {
        Set<Employee> allByBirthdayBeforeOrderBySalaryDesc =
                this.employeeRepository.
                        findAllByBirthdayBeforeOrderBySalaryDesc(localDate);
        return getSetEmployeeViewDto(allByBirthdayBeforeOrderBySalaryDesc);
    }


    private Set<EmployeeViewDto> getSetEmployeeViewDto (Set<Employee> employees){
         return employees
                .stream()
                .map(e -> this.modelMapper.map(e, EmployeeViewDto.class))
                .collect(Collectors.toSet());
    }

}
