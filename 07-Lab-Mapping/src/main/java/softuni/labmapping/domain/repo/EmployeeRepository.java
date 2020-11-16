package softuni.labmapping.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.labmapping.domain.entities.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Set<Employee> findAllByManagerId(Long id);

    List<Employee> findAllByManagerNull();

    Set<Employee>  findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate localDate);
}
