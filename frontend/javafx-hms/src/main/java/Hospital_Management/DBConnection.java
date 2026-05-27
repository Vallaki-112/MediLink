package Hospital_Management;
import java.sql.*;
public class DBConnection {
    private static final String URL =
        "jdbc:postgresql://localhost:5432/Hospital_Management_DB";

    private static final String USER =
        "postgres";

    private static final String PASSWORD =
        "postgresql23";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }    
}
