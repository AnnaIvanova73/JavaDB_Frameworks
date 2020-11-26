package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Optional<Employee> findByCard_Number(String name);


    @Query("SELECT e " +
            "FROM Employee e WHERE e.branch IS NOT NULL AND e.branch.products.size > 0 " +
            "ORDER BY CONCAT('Name ',e.firstName,' ', e.lastName),LENGTH(e.position) DESC ")
    Set<Employee> findByBranch();
}
