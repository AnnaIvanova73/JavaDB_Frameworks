package core;

import core.interfaces.FactoryTasks;
import entities.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    }

    @Override
    public boolean checkExistenceOfEmployeeEx3(EntityManager entityManager, String nameOfEmployee) {
        List<Employee> employees = entityManager
                .createQuery(CHECK_DB_FOR_EMPLOYEE_BY_NAME, Employee.class)
                .setParameter("name", nameOfEmployee)
                .getResultList();
        return employees.size() == 0;
    }

    @Override
    public String employeesWithSalaryOver5000Ex4(EntityManager entityManager) {
        builder.setLength(0);
        List<Employee> employees = entityManager
                .createQuery(EMPLOYEE_SALARY_ORDINAL_PARAMETER, Employee.class)
                .getResultList();
        employees.forEach(e -> builder.append(e.getFirstName()).append(System.lineSeparator()));
        return builder.toString().trim().equals("") ? CLEAN_DB : builder.toString().trim();

    }

    @Override
    public String extractAllEmployeesFromDepartmentsEx5(EntityManager entityManager) {
        builder.setLength(0);

        List<Employee> employees = entityManager.createQuery(EXTRACT_EMPLOYEES_BY_DEPARTMENT,Employee.class)
                .getResultList();

        employees.forEach(e ->
                builder.append(String.format(DEPARTMENTS_AND_SALARY,e.getFirstName(),e.getSalary()))
                .append(System.lineSeparator()));

        return builder.length() == 0 ? CLEAN_DB : builder.toString().trim();
    }

    @Override
    public String updateAddressByLastNameEx6(EntityManager entityManager, String lastname) {
        Address address = createAddress(ADDRESS_TEXT);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        Employee employee = entityManager.createQuery(EXTRACT_EMPLOYEE_BY_NAME, Employee.class)
                .setParameter("name", lastname)
                .getSingleResult();

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();

        return String.format(PRINT_SINGLE_ROW,employee.getFirstName(),employee.getLastName()
                ,employee.getAddress().getId(),employee.getAddress().getText());
    }

    @Override
    public String addressesWithEmployeeCountEx7(EntityManager entityManager) {
        builder.setLength(0);

        List<Address> addresses = entityManager.createQuery
                (SELECT_BY_SIZE, Address.class)
                .setMaxResults(10)
                .getResultList();
        addresses.forEach(a -> builder.append(a.getText()).
                append(", ").append(a.getTown().getName()).append(" - ")
                .append(a.getEmployees().size()).append(System.lineSeparator()));

        return builder.length() == 0 ? CLEAN_DB : builder.toString().trim();
    }

    @Override
    public String getEmployeeByIdEx8(EntityManager entityManager, int id) {
        builder.setLength(0);
        Employee employee = entityManager.find(Employee.class, id);
        builder.append(String.format(EMPLOYEE_JOB_TITLE,employee.getFirstName(),
                employee.getLastName(),employee.getJobTitle()));
        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName)).forEach(p -> builder.append(String.format(EMPLOYEE_PROJECTS,p.getName())));

       return builder.toString().trim();
    }

    @Override
    public String findLatestProjectsEx9(EntityManager entityManager) {
        builder.setLength(0);
        List<Project> resultList = entityManager
                .createQuery(FILTER_PROJECTS, Project.class)
                .setMaxResults(10)
                .getResultList();

        List<Project> sorted =
                resultList.stream().sorted(Comparator.comparing(Project::getName))
                        .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");

        for (Project project : sorted) {
            LocalDateTime startDate = project.getStartDate();
            LocalDateTime endDate = project.getEndDate();
            String start = startDate == null ? "null" : startDate.format(formatter);
            String end = endDate == null ? "null" : endDate.format(formatter);
            builder.append(String.format(PROJECTS,project.getName(),project.getDescription(),
                    start,end));
        }
        return builder.length() == 0 ? CLEAN_DB : builder.toString().trim();
    }

    @Override
    public String increaseSalariesEx10(EntityManager entityManager) {
        builder.setLength(0);
        List<Employee> employees = entityManager.createQuery(INCREASE_SALARY_BY_DEP,Employee.class)
                .setParameter("dep1",ENGINEERING)
                .setParameter("dep2",TOOL_DESIGN)
                .setParameter("dep3",MARKETING)
                .setParameter("dep4",INFORMATION_SERVICES)
                .getResultList();
        entityManager.getTransaction().begin();

        for (Employee employee : employees) {
            BigDecimal newSalary = employee.getSalary().multiply(INCREASING_PERCENT);
            employee.setSalary(newSalary);
        }

        entityManager.getTransaction().commit();
        employees.forEach(e -> builder.append(String.format
                (NAME_AND_SALARY,e.getFirstName(),e.getLastName(),e.getSalary())));
        return builder.length() == 0 ? CLEAN_DB : builder.toString().trim();
    }


    private Address createAddress(String text) {
        Address address = new Address();
        address.setText(text);
        address.setId(32);
        return address;
    }

}
