package constants;

import javax.management.Query;

public class SqlQuarries {

    public static final String TOWNS_TO_LOWER_CASE = "SELECT t FROM Town t WHERE length(t.name) <=5 ";

    public static final String CHECK_DB_FOR_EMPLOYEE_BY_NAME =
            "SELECT e FROM Employee e WHERE CONCAT(e.firstName , ' ', e.lastName)  = :name";

    public static final String EMPLOYEE_SALARY_ORDINAL_PARAMETER =
            "SELECT e FROM Employee e WHERE e.salary > 50000";

    public static final String EXTRACT_EMPLOYEES_BY_DEPARTMENT = "SELECT e FROM Employee e " +
            "JOIN Department d ON d.name = e.department.name WHERE d.name =  'Research and Development' " +
            "order by e.salary, e.id ";

    public static final String EXTRACT_EMPLOYEE_BY_NAME = "SELECT e FROM Employee e WHERE e.lastName = :name ";

    public static final String SELECT_BY_SIZE = "SELECT a FROM Address a ORDER BY a.employees.size DESC ";

    public static final String FILTER_PROJECTS = "SELECT p FROM Project p ORDER BY p.startDate DESC ";

    public static final String INCREASE_SALARY_BY_DEP = "SELECT e FROM Employee e " +
            "WHERE e.department.name IN (:dep1,:dep2,:dep3,:dep4)";

    public static final String FIND_EMPLOYEE_BY_PATTERN = "SELECT e FROM Employee e " +
            "WHERE UPPER(e.firstName) LIKE :pattern ";

    public static final String GET_TOWN_BY_ID = "SELECT t FROM Town t WHERE t.name = :town ";

    public static final String GET_ADDRESSES_BY_TOWN_NAME = "SELECT a FROM Address AS a WHERE a.town.name = :town ";

    public static final String GET_EMPLOYEE_BY_ADDRESS = "SELECT e FROM Employee AS e WHERE e.address.town.name = :town";
    public static final String GET_TOWN = "SELECT t FROM Town AS t WHERE t.name = :town" ;

    public static final String GROUP_BY = "SELECT e.department.name, MAX(e.salary) AS ms FROM Employee  e" +
            " GROUP BY e.department.name  HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000";
}

