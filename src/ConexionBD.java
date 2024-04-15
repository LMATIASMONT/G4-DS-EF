import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static final String DATABASE_NAME = "universidad";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "Fmatias9613182124.*+";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
    }
}
