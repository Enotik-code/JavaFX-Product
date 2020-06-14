package connections;

import strings.StringFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connecting {
    public static Connection connection = null;
    public static Statement statement = null;
    public static void startConnection() {
        try {
            connection = DriverManager.getConnection(StringFile.DATABASE_URL, StringFile.USER, StringFile.PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void endConnection() throws SQLException {
        statement.close();
        connection.close();
    }
}
