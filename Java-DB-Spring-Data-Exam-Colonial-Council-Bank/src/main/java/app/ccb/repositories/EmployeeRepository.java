package app.ccb.repositories;

import app.ccb.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Optional<Set<Employee>> findAllByFirstNameLikeAndLastNameLike(String firstName,
                                                                  String lastName);

    @Query("SELECT e FROM Employee e WHERE e.clients.size > 0 " +
            "ORDER BY e.clients.size DESC, e.id ASC ")
    Set<Employee> findEmployeeAndClients();
}
