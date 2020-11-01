package constants;


import java.time.format.DateTimeFormatter;

public class OutputConstantMessages {
    public static final String TASK_NAVIGATION_MSG = "--->Pick task between 2 and 13 or EXIT";
    public static final String DB_CHANGED = "Task done. Check DB in order to observe changes";
    public static final String NEGATIVE = "No";
    public static final String POSITIVE = "Yes";
    public static final String ENTER_EMPLOYEE_NAME = "Enter employee name";
    public static final String REMINDER_CLEAR_DB = "Enter lastName in order to change address, please do NOT forget to restore original records," +
            "in DB if you want to perform more tasks";
    public static final String PRINT_SINGLE_ROW = "Change in record:%n Full Name: %s %s %n Address ID: %d %n Address: %s";
    public static final String CLEAN_DB = "Uppps something went wrong.Clean DB.";
    public static final String DEPARTMENTS_AND_SALARY = "%s %s from Research and Development - $%.2f";
    public static final String EMPLOYEE_ID = "Enter employee ID: ";
    public static final String EMPLOYEE_JOB_TITLE = "%s %s - %s%n";
    public static final String EMPLOYEE_PROJECTS = "    %s%n";
    public static final String PROJECTS =  "Project name: %s\r\nProject Description: %s\r\n" +
            "Project Start Date:%s\r\nProject End Date: %s\r\n";
    public static final String NAME_AND_SALARY = "%s %s ($%.2f)%n";
    public static final String PATTERN = "Enter pattern:";
    public static final String EMPLOYEE_JOB_TITLE_SALARY = "%s %s - %s - ($%.2f)%n";
    public static final String DEP_MAX_SALARY = "%s %.2f%n";
    public static final String TRUNCATE_TABLES = "TRUNCATE TABLES AND INSERT AGAIN FOR CORRECT RESULTS BY DOCUMENT";
    public static final String TRUNCATE_OR_RELOAD = "If You wanna TRUNCATE AND RELOAD\r\n" +
            "GO TO --> resources.DATABASE.INSERT --> for insert in DB AND resources.DATABASE.TRUNCATE for truncate, " +
            "CTRL+ALT+ENTER to execute file";
    public static final String MSG_DELETE_ADDRESSES = "%d addresses in %s deleted";
    public static final String ASK_TOWN_NAME = "Enter Town name:";
    public static final String TASK_NOT_EXISTS = "No such task";
    public static final String TOWN_NOT_FOUND = "Town not found!";

}
