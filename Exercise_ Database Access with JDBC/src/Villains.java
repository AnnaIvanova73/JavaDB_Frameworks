import java.sql.ResultSet;
import java.sql.SQLException;

import static common.CommonMethods.*;
import static common.CommonQueries.*;

public abstract class Villains {

    // Task 2
    static public String executeTask2() throws SQLException {
        StringBuilder builder = new StringBuilder();
        ResultSet resultSet = executeQueryWithOneIndexParam(15, RETRIEVE_COUNT_MINIONS);

        while (resultSet.next()) {
            builder.append(String.format("%s %s", resultSet.getString("name"),
                    resultSet.getInt("minions"))).append(System.lineSeparator());
        }

        return builder.length() > 0 ? builder.toString() : "Problem with DB, couldn't find villains";
    }

    // Task 3
    static public String getMinionsNameByVillainId(int id) throws SQLException {
        StringBuilder builder = new StringBuilder();

        ResultSet resultSet = executeQueryWithOneIndexParam(id, RETRIEVE_VILLAIN_NAME_BY_ID);


        while (resultSet.next()) {
            builder.append(String.format("Villain: %s",
                    resultSet.getString("name") + System.lineSeparator()));
        }

        resultSet = executeQueryWithOneIndexParam(id, RETRIEVE_MINIONS_BY_VILLAIN_ID);

        int count = 0;
        while (resultSet.next()) {

            builder.append(String.format("%d. %s %d", ++count, resultSet.getString("name"),
                    resultSet.getInt("age"))).append(System.lineSeparator());
        }

        return builder.length() > 0 ? builder.toString() : "No villain with ID 10 exists in the database." + System.lineSeparator();
    }


    static public String removeVillain(int id) throws SQLException {
        ResultSet resultSet = executeQueryWithOneIndexParam(id, RETRIEVE_VILLAIN_NAME_BY_ID);

        if (!resultSet.next()) {
            return "No such villain was found";
        } else {

            int numberOfRowsBeforeDelete = getNumberOfRows("minion_id","minions_villains");
            int numberOfRowsAfterDelete = 0;

            if (numberOfRowsBeforeDelete > 0) {
                deleteFromDB("minions_villains", "villain_id", id);
                numberOfRowsAfterDelete = getNumberOfRows("minion_id","minions_villains");
            }

            String name = resultSet.getString("name");
            deleteFromDB("villains", "id", id);

            return String.format("%s was deleted \n" +
                    "%d minions released", name, numberOfRowsBeforeDelete - numberOfRowsAfterDelete);
        }
    }
}
