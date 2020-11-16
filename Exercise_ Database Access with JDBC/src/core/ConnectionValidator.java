package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

abstract public class ConnectionValidator {
    public static Connection connection = null;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public ConnectionValidator() {
    }

    public static void connect (){
        Scanner scan = new Scanner(System.in);

        System.out.printf("Get connected to minions_db@localhost %nEnter username: %n");
        String username = scan.nextLine();
        System.out.printf("Now enter your password: %n");
        String password = scan.nextLine();

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        try {
            connection = DriverManager.getConnection(DB_URL, properties);
            System.out.println("ACCESS GRANTED");
        } catch (SQLException throwables) {
            System.err.println("ACCESS DENIED");
            System.exit(0);
        }
    }
}
