import java.sql.*;

class DBConnection {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:event.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
