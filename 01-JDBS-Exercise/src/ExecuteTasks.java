import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static common.CommonMethods.*;

public abstract class ExecuteTasks {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static String tasksName = "---> Task - %s %n";

    public static void executeTask9() throws IOException, SQLException {
        System.out.println("First create procedure with this script in DB: ");
        System.out.println("CREATE PROCEDURE usp_get_older( minion_id INT)\n" +
                "BEGIN\n" +
                "    UPDATE minions\n" +
                "        SET age = age + 1\n" +
                "    WHERE id = minion_id;\n" +
                "end;");
        System.out.println("Enter id: ");
        int id = Integer.parseInt(reader.readLine());
        System.out.println(Minions.callProcedure(id));
    }

    public static void executeTask8() throws IOException, SQLException {
        System.out.printf(tasksName, "08.Increase Minions Age%n");
        System.out.println("Enter ids:");

        Integer[] arg = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt)
                .boxed().toArray(Integer[]::new);
        Minions.updateNameAndAgeById("minions", "age", "id", 1, arg);
        System.out.println(retrieveAllRecordsByParams("name", "age", "minions"));
    }


    public static void executeTask7() throws SQLException {
        System.out.printf(tasksName, "07.Print All Minion Names");
        System.out.println(Minions.mergeAllNames("name", "minions"));
    }

    static void executeTask6() throws IOException, SQLException {
        System.out.printf(tasksName, "06.Remove Villain");
        System.out.println("Enter id");
        int id = Integer.parseInt(reader.readLine());
        System.out.println(Villains.removeVillain(id));
    }


    static void executeTask5() throws IOException, SQLException {
        System.out.printf(tasksName, "05.Change Town Names Casing ");
        System.out.println("Enter country:");
        String country = reader.readLine().trim();
        List<String> countries =
                returnListOfChangedToUpperCaseRecords("name", "towns", country.trim());
        String output = countries.size() > 0 ? "%d town names were affected.%n" : "No town names were affected.%n";
        System.out.printf(output,countries.size());
        System.out.println(countries);
    }

    static void executeTask4() throws IOException, SQLException {
        System.out.printf(tasksName, "04.Add Minion");
        System.out.println("Enter input (Minion parameters and Villain name):");
        try{
            String minionInput = reader.readLine().trim();
            String villainInput = reader.readLine().trim();

            String[] minion = minionInput.split(":");
            String[] villain = villainInput.split(":");

            String minionName = Minions.insertMinionToDB(minion[1].trim(), villain[1].trim());
            System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villain[1].trim());
        }catch (Exception e){
            System.err.println("ERROR!INTERVALS OR WHITESPACES! CHECK YOUR INPUT AND TRY AGAIN!");
        }
    }

    static void executeTask3() throws IOException, SQLException {
        System.out.printf(tasksName, "03.Get Minion Names");
        System.out.println("Enter villain id:");
        int id = Integer.parseInt(reader.readLine());
        System.out.printf("Output:%n" + Villains.getMinionsNameByVillainId(id));
    }

    static void executeTask2() throws SQLException {
        System.out.printf(tasksName, "02.Get Villains’Names");
        System.out.printf("Output:%n" + Villains.executeTask2());
    }

    static void executeTask1() {
        System.out.printf(tasksName + " -->" +
                " Press Ctrl+Alt+U for pop diagram%n", "04.Change Town Names Casing");
    }
}
