import core.ConnectionValidator;
import core.ConnectorDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws SQLException, IOException {

        ConnectorDriver.connectDriver();
        ConnectionValidator.connect();


        String tasksPointer = "--->Pick between 1 and 9 or EXIT";
        System.out.println(tasksPointer);

        String input;
        while (!(input = reader.readLine()).equals("EXIT")) {

            int numberOfTask = Integer.parseInt(input);
            switch (numberOfTask) {
                case 1:
                    ExecuteTasks.executeTask1();
                    break;
                case 2:
                    ExecuteTasks.executeTask2();
                    break;
                case 3:
                    ExecuteTasks.executeTask3();
                    break;
                case 4:
                    ExecuteTasks.executeTask4();
                    break;
                case 5:
                    ExecuteTasks.executeTask5();
                    break;
                case 6:
                    ExecuteTasks.executeTask6();
                    break;
                case 7:
                    System.out.println("TRUNCATE TABLES AND INSERT AGAIN FOR CORRECT RESULTS BY DOCUMENT");
                    ExecuteTasks.executeTask7();
                    break;
                case 8:
                    ExecuteTasks.executeTask8();
                    break;
                case 9:
                    ExecuteTasks.executeTask9();
                    break;
                default:
                    System.err.println("No such task");
                    break;
            }
            System.out.println(tasksPointer);
        }



    }
}

