package constants;

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
    public static final String INCREASE_SALARY = "SELECT p FROM Project p ORDER BY p.startDate DESC ";
    public static final String INCREASE_SALARY_BY_DEP = "SELECT e FROM Employee e " +
            "WHERE e.department.name IN (:dep1,:dep2,:dep3,:dep4)";
}
