package softuni.labmapping.services;

import softuni.labmapping.domain.dtos.seed.EmployeeSeedDto;
import softuni.labmapping.domain.dtos.view.EmployeeViewDto;
import softuni.labmapping.domain.entities.Employee;

import java.time.LocalDate;
import java.util.Set;

public interface EmployeeService {

    void seedDB(EmployeeSeedDto employeeSeedDto);
    Employee createEntity (EmployeeSeedDto employeeSeedDto);
    Set<EmployeeViewDto> getAllEmployeesByManagerId(Long id);
    Set<EmployeeViewDto> getAllBornBefore(LocalDate localDate);
}
