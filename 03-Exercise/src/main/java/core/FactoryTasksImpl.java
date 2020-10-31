package core;

import core.interfaces.FactoryTasks;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

import static constants.SqlQuarries.*;

public class FactoryTasksImpl implements FactoryTasks {
    private static StringBuilder builder = new StringBuilder();
    @Override
    public void townsToLowerCase(EntityManager entityManager){
        List<Town> towns = entityManager
                .createQuery(TOWNS_TO_LOWER_CASE,Town.class)
                .getResultList();
        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);
        for (Town town : towns) {
            town.setName(town.getName().toUpperCase());
        }
        towns.forEach(entityManager::merge);
        entityManager.getTransaction().commit();
    }

    @Override
    public boolean checkExistenceOfEmployee(EntityManager entityManager, String nameOfEmployee) {
        List<Employee> employees = entityManager.createQuery(CHECK_DB_FOR_EMPLOYEE_BY_NAME,Employee.class)
                .setParameter("name",nameOfEmployee)
                .getResultList();
        return employees.size() == 0;
    }

    @Override
    public String employeesWithSalaryOver5000(EntityManager entityManager) {
        builder.setLength(0);



        return builder.toString().trim();
    }


}
