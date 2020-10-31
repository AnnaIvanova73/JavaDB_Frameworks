package constants;

public class SqlQuarries {

    public static final String TOWNS_TO_LOWER_CASE  = "SELECT t FROM Town t WHERE length(t.name) <=5 ";

    public static final String CHECK_DB_FOR_EMPLOYEE_BY_NAME =
            "SELECT e FROM Employee e WHERE CONCAT(e.firstName , ' ', e.lastName)  = :name";

    public static final String EMPLOYEE_SALARY_ORDINAL_PARAMETER =
            "SELECT e FROM Employee e WHERE e.salary > 50000";

    public static final String SUCCESSFULLY_ADDED_PRESENT = "Successfully added Present: %s!";

    public static final String PRESENT_DONE = "Present %s is %s. ";

    public static final String COUNT_BROKEN_INSTRUMENTS = "%d instrument/s have been broken while working on it!";
}
