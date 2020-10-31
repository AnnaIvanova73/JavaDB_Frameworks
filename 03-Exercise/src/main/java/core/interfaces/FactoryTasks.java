package core.interfaces;

import entities.Address;

import javax.persistence.EntityManager;
import java.io.IOException;

public interface FactoryTasks {

     void townsToLowerCaseEx2(EntityManager entityManager);
     boolean checkExistenceOfEmployeeEx3(EntityManager entityManager, String nameOfEmployee);
     String employeesWithSalaryOver5000Ex4(EntityManager entityManager);
     String extractAllEmployeesFromDepartmentsEx5(EntityManager entityManager);
     public Address createAddress (String text);
     String updateAddressByLastNameEx6(EntityManager entityManager, String lastname);

}
