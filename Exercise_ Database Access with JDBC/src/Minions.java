import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static common.CommonMethods.*;
import static common.CommonQueries.*;
import static core.ConnectionValidator.connection;

public abstract class Minions {
    static public String insertMinionToDB(String minionParams,String villainName) throws SQLException {

        String[] tokens = minionParams.trim().split("\\s+");
        String minionName = tokens[0];
        int age = Integer.parseInt(tokens[1]);
        String townName = tokens[2];


        if (retrieveIdByName(townName,"towns") == -1) {
            insertEntity(townName,"towns");
            System.out.printf("Town %s was added to the database.%n",townName);
        }

        int townId = retrieveIdByName(townName,"towns");
        insertIntoMinions(minionName, age, townId);

        if(retrieveIdByName(villainName,"villains") == -1){
            insertEntity(villainName,"villains");
            System.out.printf("Villain %s was added to the database.%n",villainName);
        }
        int minionId = retrieveIdByName(minionName,"minions");
        int villainId = retrieveIdByName(villainName,"villains");
        insertIntoMinionsVillains (minionId,villainId);

        return minionName;
    }

    private static void insertIntoMinionsVillains(int minionId, int villainId) throws SQLException {
        statement = connection.prepareStatement(String.format(INSERT_INTO_MANY_TO_MANY,"minions_villains",
                "minion_id","villain_id"));
        statement.setInt(1,minionId);
        statement.setInt(2,villainId);
        statement.execute();
    }


    private static void insertIntoMinions(String name, int age, int id) throws SQLException {
        statement = connection.prepareStatement(INSERT_INTO_MINIONS);
        statement.setString(1, name);
        statement.setInt(2, age);
        statement.setInt(3, id);
        statement.execute();
    }

    private static void insertEntity(String name, String table) throws SQLException {

        String secondColumn = table.equals("villains") ? "evilness_factor" : "country";
        statement = connection.prepareStatement(String.format(INSERT_NAME_INTO_COLUMN, table,"name",secondColumn));
        statement.setString(1, name);

        String secondParameter = table.equals("villains") ? "evil" : "Unknown";
        statement.setString(2, secondParameter);

        statement.executeUpdate();
    }


    public static String mergeAllNames(String column, String table) throws SQLException {
        StringBuilder builder = new StringBuilder();

        statement = connection.prepareStatement((String.format(RETRIEVE_DATA_BY_COLUMN_ASC, column, table)));
        ResultSet naturalOrder = statement.executeQuery();
        List<String> nameMinions = new ArrayList<>();

        while (naturalOrder.next()) {
            nameMinions.add(naturalOrder.getString("name"));
        }
        int numberOfRows = nameMinions.size();
        int middleIndex = numberOfRows / 2;

        List<String> result = new ArrayList<>();
        for (int i = 0; i < middleIndex; i++) {
            result.add(nameMinions.get(i));
            result.add(nameMinions.get(nameMinions.size() - 1 - i));
        }

        if (numberOfRows % 2 != 0) {
            result.add(nameMinions.get(middleIndex));
        }
        result.forEach(s -> builder.append(s).append(System.lineSeparator()));
        return builder.toString().trim();
    }


    public static void updateNameAndAgeById(String table, String column,
                                            String updateBy, int updateNumber, Integer... ids) throws SQLException {
        for (Integer id : ids) {
            toLowerCase(table,updateBy,id);
            updateNumberByGivenParameters(table,column,updateNumber,updateBy,id);
        }
    }



    public static String callProcedure(int id) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(CALL_USP_GET_OLDER);
        callableStatement.setInt(1,id);
        callableStatement.execute();

        return retrieveMinionName(id);
    }

    private static String retrieveMinionName(int id) throws SQLException {
        ResultSet rs = executeQueryWithOneIndexParam(id, RETRIEVE_MINION_NAME_BY_ID);

        if(rs.next()){
            return rs.getString("name") + " " + rs.getString("age");
        }else{
            return "Nan";
        }
    }
}
