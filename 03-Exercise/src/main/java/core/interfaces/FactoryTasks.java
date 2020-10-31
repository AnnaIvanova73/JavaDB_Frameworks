package core.interfaces;

import javax.persistence.EntityManager;
import java.io.IOException;

public interface FactoryTasks {

     void townsToLowerCase(EntityManager entityManager);
     boolean checkExistenceOfEmployee (EntityManager entityManager,String nameOfEmployee);
     String employeesWithSalaryOver5000 (EntityManager entityManager);

}
