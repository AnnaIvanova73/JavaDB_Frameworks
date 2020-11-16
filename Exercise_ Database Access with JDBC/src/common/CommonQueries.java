package common;

public class CommonQueries {
    public static final String RETRIEVE_COUNT_MINIONS = "SELECT v.name, COUNT( mv.minion_id) minions " +
            "FROM villains v " +
            "JOIN minions_villains mv on v.id = mv.villain_id " +
            "GROUP BY v.name " +
            "HAVING minions > ? " +
            "ORDER BY minions DESC ";

    public static final String RETRIEVE_MINIONS_BY_VILLAIN_ID =
            "SELECT m.name, m.age " +
                    "           FROM minions m " +
                    "    JOIN minions_villains mv on m.id = mv.minion_id " +
                    "    WHERE mv.villain_id = ? ";

    public static final String RETRIEVE_VILLAIN_NAME_BY_ID  = "SELECT v.name FROM villains v WHERE v.id = ? ";
    public static final String RETRIEVE_MINION_NAME_BY_ID  = "SELECT m.name, m.age FROM minions m WHERE m.id = ? ";


    public static final String INSERT_NAME_INTO_COLUMN = "INSERT INTO %s (%s, %s) VALUES (?,?) ";

    public static final String INSERT_INTO_MINIONS = "INSERT INTO minions (name, age, town_id) VALUES (?,?,?) ";

    public static final String RETRIEVE_ID_BY_NAME = "SELECT id FROM " + "%s" + " WHERE name = ? ";

    public static final String INSERT_INTO_MANY_TO_MANY = "INSERT INTO %s (%s, %s) VALUES (?,?) ";

    public static final String CHANGE_TO_UPPERCASE = "SELECT UPPER(t.%s) name FROM %s t WHERE t.country = ? ";

    public static final String DELETE_BY_NUMBER_PARAMETER = "DELETE FROM %s WHERE %s = ? ";

    public static final String RETRIEVE_DATA_BY_COLUMN_ASC = "SELECT %s FROM %s ";

    public static final String INCREMENT_NUMBER = "UPDATE %s SET %s = %s + %d WHERE %s = %d ";

    public static final String NAME_TO_LOWER_CASE = "UPDATE %s SET name = LOWER(name) WHERE %s = ? ";

    public static final String RETRIEVE_ALL_STRING_AND_INT_COLUMN = "SELECT %s, %s FROM %s";

    public static final String CALL_USP_GET_OLDER = "CALL usp_get_older(?)";
}
