package core.interfaces;

import javax.persistence.EntityManager;
import java.io.IOException;

public interface FactoryTasks {

     void townsToLowerCaseEx2(EntityManager entityManager);
     boolean checkExistenceOfEmployeeEx3(EntityManager entityManager, String nameOfEmployee);
     String employeesWithSalaryOver5000Ex4(EntityManager entityManager);

}