package alararestaurant.service;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Boolean employeesAreImported() {
        // TODO : Implement me
        return false;
//        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() {
        // TODO : Implement me
        return null;
    }

    @Override
    public String importEmployees(String employees) {
        // TODO : Implement me
        return null;
    }
}
