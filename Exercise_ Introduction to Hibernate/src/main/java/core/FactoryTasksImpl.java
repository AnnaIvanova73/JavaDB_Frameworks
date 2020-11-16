package core;

import core.interfaces.FactoryTasks;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        return getString();

    }

    @Override
    public String extractAllEmployeesFromDepartmentsEx5(EntityManager entityManager) {
        builder.setLength(0);

        List<Employee> employees = entityManager.createQuery(EXTRACT_EMPLOYEES_BY_DEPARTMENT, Employee.class)
                .getResultList();

        employees.forEach(e ->
                builder.append(String.format(DEPARTMENTS_AND_SALARY, e.getFirstName(), e.getLastName(), e.getSalary()))
                        .append(System.lineSeparator()));

        return getString();
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

        return String.format(PRINT_SINGLE_ROW, employee.getFirstName(), employee.getLastName()
                , employee.getAddress().getId(), employee.getAddress().getText());
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
                .append(a.getEmployees().size()).append(" employees").append(System.lineSeparator()));

        return getString();
    }

    @Override
    public String getEmployeeByIdEx8(EntityManager entityManager, int id) {
        builder.setLength(0);
        Employee employee = entityManager.find(Employee.class, id);
        builder.append(String.format(EMPLOYEE_JOB_TITLE, employee.getFirstName(),
                employee.getLastName(), employee.getJobTitle()));
        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName)).forEach(p -> builder.append(String.format(EMPLOYEE_PROJECTS, p.getName())));

        return getString();
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
            builder.append(String.format(PROJECTS, project.getName(), project.getDescription(),
                    start, end));
        }
        return getString();
    }

    @Override
    public String increaseSalariesEx10(EntityManager entityManager) {
        builder.setLength(0);
        List<Employee> employees = entityManager.createQuery(INCREASE_SALARY_BY_DEP, Employee.class)
                .setParameter("dep1", ENGINEERING)
                .setParameter("dep2", TOOL_DESIGN)
                .setParameter("dep3", MARKETING)
                .setParameter("dep4", INFORMATION_SERVICES)
                .getResultList();
        entityManager.getTransaction().begin();

        for (Employee employee : employees) {
            BigDecimal newSalary = employee.getSalary().multiply(INCREASING_PERCENT);
            employee.setSalary(newSalary);
        }

        entityManager.getTransaction().commit();
        employees.forEach(e -> builder.append(String.format
                (NAME_AND_SALARY, e.getFirstName(), e.getLastName(), e.getSalary())));
        return getString();
    }

    @Override
    public String findEmployeesByFirstNameEx11(EntityManager entityManager, String input) {
        builder.setLength(0);
        List<Employee> employees = entityManager.createQuery(
                FIND_EMPLOYEE_BY_PATTERN, Employee.class)
                .setParameter("pattern", input + "%")
                .getResultList();

        employees.forEach(e -> builder.append(String.format(EMPLOYEE_JOB_TITLE_SALARY,
                e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary())));

        return getString();
    }

    @Override
    public String employeesMaximumSalariesEx12(EntityManager entityManager) {
        builder.setLength(0);
        Query query = entityManager.createQuery(GROUP_BY);
        List employees = query.getResultList();
        for (Object employee : employees) {
            Object[] dep = (Object[]) employee;
            String format = String.format(DEP_MAX_SALARY, (String) dep[0], (BigDecimal) dep[1]);
            builder.append(format);
        }
        return getString();
    }

    @Override
    public String deleteAddressByTown(EntityManager entityManager, String townName) {

        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery(
                GET_EMPLOYEE_BY_ADDRESS, Employee.class)
                .setParameter("town", townName)
                .getResultList();

        if (employees.isEmpty()) {
            return TOWN_NOT_FOUND;
        }

        for (Employee employee : employees) {
            employee.setAddress(null);
        }

        List<Address> addresses = entityManager.createQuery(GET_ADDRESSES_BY_TOWN_NAME, Address.class)
                .setParameter("town", townName)
                .getResultList();

        for (Address address : addresses) {
            entityManager.remove(address);
        }

        Town town = entityManager.createQuery(GET_TOWN, Town.class)
                .setParameter("town", townName)
                .getSingleResult();

        entityManager.remove(town);


        entityManager.getTransaction().commit();
        builder.setLength(0);
        builder.append(String.format(MSG_DELETE_ADDRESSES, addresses.size(), townName));
        return getString();
    }


    private String getString() {
        return builder.length() == 0 ? CLEAN_DB : builder.toString().trim();
    }

    private Address createAddress(String text) {
        Address address = new Address();
        address.setText(text);
        address.setId(32);
        return address;
    }
}
