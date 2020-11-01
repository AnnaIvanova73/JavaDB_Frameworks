package constants;

import entities.Project;
import jdk.jfr.Description;

import java.time.format.DateTimeFormatter;

public class OutputConstantMessages {
    private static final DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:ns");
    public static final String TASK_NAVIGATION_MSG = "Enter number of task, else enter EXIT";
    public static final String DB_CHANGED = "Task done. Check DB in order to observe changes";
    public static final String NEGATIVE = "No";
    public static final String POSITIVE = "Yes";
    public static final String ENTER_EMPLOYEE_NAME = "Enter employee name";
    public static final String REMINDER_CLEAR_DB = "Enter lastName in order to change address, please do NOT forget to restore original records," +
            "in DB if you want to perform more tasks";
    public static final String PRINT_SINGLE_ROW = "Change in record:%n Full Name: %s %s %n Address ID: %d %n Address: %s";
    public static final String CLEAN_DB = "Clean DB, couldn't find result";
    public static final String DEPARTMENTS_AND_SALARY = "%s from Research and Development - $%.2f";
    public static final String EMPLOYEE_ID = "Enter employee ID: ";
    public static final String EMPLOYEE_JOB_TITLE = "%s %s - %s%n";
    public static final String EMPLOYEE_PROJECTS = "    %s%n";
    public static final String PROJECTS =  "Project name: %s\r\nProject Description: %s\r\n" +
            "Project Start Date:%s\r\nProject End Date: %s\r\n";
    public static final String NAME_AND_SALARY = "%s %s ($%.2f)%n";
}
