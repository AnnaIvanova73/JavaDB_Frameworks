package alararestaurant.repository;

import alararestaurant.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Optional<Employee> findByNameLike (String name);

    @Query("SELECT e FROM Employee e  JOIN Order o ON e.id = o.employee.id " +
            " WHERE e.position.name = 'Burger Flipper' AND e.id = o.employee.id order by " +
            "e.name,o.id asc ")
    Set<Employee> findAllByPosition();
}
