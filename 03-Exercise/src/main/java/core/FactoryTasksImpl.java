package core;

import core.interfaces.FactoryTasks;
import entities.Address;
import entities.Department;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

import static constants.ConstantValues.*;
import static constants.OutputConstantMessages.*;
import static constants.SqlQuarries.*;

public class FactoryTasksImpl implements FactoryTasks {

    private final static StringBuilder builder = new StringBuilder();

    @Override
    public void townsToLowerCaseEx2(EntityManager entityManager) {
        List<Town> towns = entityManager
                .createQuery(TOWNS_TO_LOWER_CASE, Town.class)
                .getResultList();
        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);
        for (Town town : towns) {
            town.setName(town.getName().toUpperCase());
        }
        towns.forEach(entityManager::merge);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public boolean checkExistenceOfEmployeeEx3(EntityManager entityManager, String nameOfEmployee) {
        List<Employee> employees = entityManager
                .createQuery(CHECK_DB_FOR_EMPLOYEE_BY_NAME, Employee.class)
                .setParameter("name", nameOfEmployee)
                .getResultList();
        entityManager.close();
        return employees.size() == 0;
    }

    @Override
    public String employeesWithSalaryOver5000Ex4(EntityManager entityManager) {
        builder.setLength(0);
        List<Employee> employees = entityManager
                .createQuery(EMPLOYEE_SALARY_ORDINAL_PARAMETER, Employee.class)
                .getResultList();
        employees.forEach(e -> builder.append(e.getFirstName()).append(System.lineSeparator()));
        entityManager.close();
        return builder.toString().trim().equals("") ? "Clean DB, couldn't find result" : builder.toString().trim();

    }

    @Override
    public String extractAllEmployeesFromDepartmentsEx5(EntityManager entityManager) {
        builder.setLength(0);

        List<Employee> employees = entityManager.createQuery("SELECT e " +
                "FROM Employee e " +
                "JOIN Department d ON d.name = e.department.name WHERE d.name =  'Research and Development' " +
                "order by e.salary, e.id ", Employee.class)
                .getResultList();

        employees.forEach(e -> {
            builder.append(e.getFirstName())
                    .append(" from Research and Development - $")
                    .append(e.getSalary())
                    .append(System.lineSeparator());
        });

        entityManager.close();
        return builder.length() == 0 ? "Clean DB, couldn't find result" : builder.toString().trim();
    }

    @Override
    public String updateAddressByLastNameEx6(EntityManager entityManager, String lastname) {
        builder.setLength(0);
        Address address = createAddress(ADDRESS_TEXT);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        Integer id = address.getId();

        Object object = entityManager.createQuery("" +
                "SELECT e FROM Employee e WHERE e.lastName = :name ")
                .setParameter("name", lastname)
                .getSingleResult();

        Employee employee = (Employee) object;
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
        entityManager.close();
        Address address1 = employee.getAddress();
        builder.append("Changed row:").append(System.lineSeparator())
                .append("Full name: ")
                .append(employee.getFirstName())
                .append(" ")
                .append(employee.getLastName())
                .append(System.lineSeparator())
                .append("Address: ")
                .append(address1.getText())
                .append(System.lineSeparator())
                .append("Address ID: ")
                .append(address1.getId());
        return builder.toString().trim();
    }

    @Override
    public Address createAddress(String text) {
        Address address = new Address();
        address.setText(text);
        return address;
    }

}
