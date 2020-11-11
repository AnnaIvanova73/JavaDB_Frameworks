package common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static common.CommonQueries.*;
import static core.ConnectionValidator.connection;


public class CommonMethods {
    public static PreparedStatement statement;

    public static int retrieveIdByName(String entityName, String tableName) throws SQLException {
        statement = connection.prepareStatement(String.format(RETRIEVE_ID_BY_NAME, tableName));
        statement.setString(1, entityName);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getInt("id") : -1;
    }


    public static ResultSet executeQueryWithOneIndexParam(int id, String command) throws SQLException {
        statement = connection.prepareStatement(command);
        statement.setInt(1, id);
        return statement.executeQuery();
    }

    public static List<String> returnListOfChangedToUpperCaseRecords(String column, String table, String prm) throws SQLException {
        List<String> list = new ArrayList<>();
        statement = connection.prepareStatement(String.format(CHANGE_TO_UPPERCASE, column, table));
        statement.setString(1, prm);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            list.add(rs.getString(column));
        }
        return list;
    }

    public static boolean deleteFromDB(String table, String column, int number) throws SQLException {
        statement = connection.prepareStatement(String.format(DELETE_BY_NUMBER_PARAMETER, table, column));
        statement.setInt(1, number);
        return statement.execute();

    }


    public static int getNumberOfRows(String column, String table) throws SQLException {
        Statement stmt = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery((String.format(RETRIEVE_DATA_BY_COLUMN_ASC, column, table)));
        rs.last();
        return rs.getRow();
    }

    public static boolean updateNumberByGivenParameters(String table,
                                                        String column,
                                                        int updateValue,
                                                        String columnUpdateBy, int id) throws SQLException {
        statement = connection.prepareStatement(
                String.format(INCREMENT_NUMBER, table, column, column, updateValue, columnUpdateBy, id));
        return statement.execute();
    }

    public static String retrieveAllRecordsByParams(String p1, String p2, String p3) throws SQLException {
        statement = connection.prepareStatement(String.format(RETRIEVE_ALL_STRING_AND_INT_COLUMN, p1, p2, p3));
        ResultSet rs = statement.executeQuery();
        StringBuilder builder = new StringBuilder();
        while (rs.next()) {
            builder
                    .append(rs.getString("name"))
                    .append(" ").append(rs.getInt("age"))
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    public static boolean toLowerCase(String table, String columnUpdateBy, int id) throws SQLException {
        statement = connection.prepareStatement(
                String.format(NAME_TO_LOWER_CASE, table, columnUpdateBy));
        statement.setInt(1, id);
        return statement.execute();
    }

}
