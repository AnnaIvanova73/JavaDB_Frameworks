package com.example.lab.domain.repositories;

import com.example.lab.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee findByFirstNameAndLastName (String firstName, String lastName);
}
