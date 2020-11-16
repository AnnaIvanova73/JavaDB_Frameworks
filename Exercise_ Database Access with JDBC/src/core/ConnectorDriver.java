package core;

public abstract class ConnectorDriver  {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void connectDriver() {
        try {
            Class.forName(DB_DRIVER);
            System.out.println("Driver on, we're ready to go");
        } catch (ClassNotFoundException e) {
            System.out.printf("You have problem with driver, " +
                    "connection not possible. Check the message%n%s", e.getMessage());
        }
    }
}
