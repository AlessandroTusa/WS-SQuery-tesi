import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
    static private final String dbLoc = "jdbc:sqlite:src/main/resources/DBSQuery.db";
    static private DBconnect instance = null;

    private DBconnect() {
        instance = this;
    }
    public static DBconnect getInstance() {
        if (instance == null)
            return new DBconnect();
        else {
            return instance;
        }
    }
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dbLoc);
        } catch (SQLException e) {
            throw new SQLException("Cannot get connection to " + dbLoc, e);
        }
    }
}