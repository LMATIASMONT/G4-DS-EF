import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = ConexionBD.getConnection();

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + ConexionBD.DATABASE_NAME);
            stmt.executeUpdate("USE " + ConexionBD.DATABASE_NAME);

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS estudiantes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nombre VARCHAR(255) NOT NULL," +
                    "edad INT NOT NULL," +
                    "universidad VARCHAR(255) NOT NULL," +
                    "carrera VARCHAR(255) NOT NULL" +
                    ")");

            conn.close();

            System.out.println("Base de datos y tabla creadas correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
