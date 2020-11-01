package core.interfaces;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.io.IOException;

public interface FactoryTasks {

     void townsToLowerCaseEx2(EntityManager entityManager);
     boolean checkExistenceOfEmployeeEx3(EntityManager entityManager, String nameOfEmployee);
     String employeesWithSalaryOver5000Ex4(EntityManager entityManager);
     String extractAllEmployeesFromDepartmentsEx5(EntityManager entityManager);
     String updateAddressByLastNameEx6(EntityManager entityManager, String lastname);
     String addressesWithEmployeeCountEx7 (EntityManager entityManager);
     String getEmployeeByIdEx8(EntityManager entityManager, int id);
     String findLatestProjectsEx9(EntityManager entityManager);
     String increaseSalariesEx10(EntityManager entityManager);

}
